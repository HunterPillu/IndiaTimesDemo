package com.example.myapplication.view.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.common_util.DebugUtils
import com.example.myapplication.common_util.Status
import com.example.myapplication.common_util.Utils
import com.example.myapplication.model.DcbSubscription
import com.example.myapplication.view.adapter.SubscriptionAdapter
import com.example.myapplication.viewmodel.SubscriptionViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment
import kotlinx.android.synthetic.main.activity_subscription.*
import kotlinx.android.synthetic.main.bottomsheet_purchase.*
import kotlinx.android.synthetic.main.bottomsheet_subscription.*


class SubscriptionActivity : DcbBaseActivity() {
    private val TAG: String = SubscriptionActivity::class.java.name
    private lateinit var mViewModel: SubscriptionViewModel
    private lateinit var mAdapter: SubscriptionAdapter
    private lateinit var bsmSubscribe: BottomSheetBehavior<MaterialCardView>
    private lateinit var bsmPurchase: BottomSheetBehavior<MaterialCardView>

    private lateinit var progress: ProgressDialog
    private val mPaymentMethods = getPaymentMethods()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        initToolbar(getString(R.string.title_digital_service_name), null)
        initViews()
        initBottomSheet()
        setOnClickListeners()
        setUpViewModel()
        observeLiveData()
        observeApiError()
        fetchSubscription()
    }

    private fun initBottomSheet() {
        bsmSubscribe = BottomSheetBehavior.from(cvBottomSheetSubscribe)
        bsmPurchase = BottomSheetBehavior.from(cvBottomSheetPurchase)
        bsmSubscribe.state = BottomSheetBehavior.STATE_HIDDEN
        bsmPurchase.state = BottomSheetBehavior.STATE_HIDDEN
        //bottomSheetBehavior.setDraggable(false);


        bsmSubscribe.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        mAdapter.updateSelectedItem(-1)
                    }

                    else -> {

                    }
                }
            }
        })

        bsmPurchase.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        mAdapter.updateSelectedItem(-1)
                    }

                    else -> {

                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        finish()
    }

    private fun observeLiveData() {
        mViewModel.statusLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    if (it.data == 1) {
                        pbProgress.visibility = View.VISIBLE
                    } else {
                        progress.show()
                    }
                }
                Status.SUCCESS -> {
                    progress.dismiss()

                    if (it.data == 2 && null != mViewModel.responseOTP) {

                    } else if (it.data == 1 && mViewModel.mSubscriptions?.isEmpty() == false) {
                        pbProgress.visibility = View.GONE
                        showHideList(true)
                        mAdapter.addList(mViewModel.mSubscriptions!!)
                    }
                }
                Status.ERROR -> {
                    progress.dismiss()
                    pbProgress.visibility = View.GONE
                    if (it.data == 1) {
                        if (!Utils.IS_API_NOT_WORKING) {
                            showHideList(false)
                        }
                    }
                    showErrorDialog(it.data, null)
                }
                else -> {

                }
            }
        }
    }

    private fun showHideList(canShow: Boolean) {
        if (canShow) {
            rvList.visibility = View.VISIBLE
            tvMessage.visibility = View.GONE
        } else {
            rvList.visibility = View.GONE
            tvMessage.visibility = View.VISIBLE
        }
    }

    private fun observeApiError() {
        mViewModel.apiErrorLiveData.observe(this) {
            progress.dismiss()
            pbProgress.visibility = View.GONE
            showErrorDialog(it.code, it.message)
        }
    }

    private fun showErrorDialog(type: Int?, msg: String?) {
        var message = msg
        val animationJson: String
        if (type == 1) {
            //means authenticate error
            message = getString(R.string.login_failed)
            animationJson = "forget_password_animation.json"
        } else if (type == 2) {
            message = mViewModel.responseOTP?.message!!
            animationJson = "forget_password_animation.json"
        } else {
            //something else
            animationJson = "forget_password_animation.json"
        }

        val mDialog = BottomSheetMaterialDialog.Builder(this)
            .setTitle(getString(R.string.title_oops), TextAlignment.START)
            .setMessage(message!!, TextAlignment.START)
            .setCancelable(true)
            .setAnimation(animationJson)
            .setPositiveButton(
                getString(R.string.text_ok)
            ) { dialogInterface, which ->
                // Delete Operation
                dialogInterface.dismiss()
            }
            /* .setNegativeButton(
                 "Cancel", R.drawable.ic_close
             ) { dialogInterface, which -> dialogInterface.dismiss() }*/
            .build()

        // Show Dialog
        mDialog.show()

        val animationView = mDialog.animationView
        animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }

    private fun getDummyData(): MutableList<DcbSubscription> {
        val list: MutableList<DcbSubscription> = arrayListOf()
        if (Utils.IS_API_NOT_WORKING) {
            list.add(DcbSubscription("ABC", "Google Play Games", "99"))
            list.add(DcbSubscription("DEF", "Disney+ Hotstar", "999"))
            list.add(DcbSubscription("IJK", "Amazon Prime", "1399"))
            list.add(DcbSubscription("XYZ", "Netflix", "1500"))
        }
        return list
    }

    private fun initViews() {
        mAdapter = SubscriptionAdapter(getDummyData(), ::onListItemClicked)
        rvList.adapter = mAdapter
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(true) // disable dismiss by tapping outside of the dialog
    }

    private fun setOnClickListeners() {

        llTopBarSubscription.setOnClickListener {
            if (bsmSubscribe.state == BottomSheetBehavior.STATE_EXPANDED) bsmSubscribe.state =
                BottomSheetBehavior.STATE_HIDDEN
            else bsmSubscribe.state = BottomSheetBehavior.STATE_EXPANDED
        }

        llTopBarPurchase.setOnClickListener {
            if (bsmPurchase.state == BottomSheetBehavior.STATE_EXPANDED) bsmPurchase.state =
                BottomSheetBehavior.STATE_HIDDEN
            else bsmPurchase.state = BottomSheetBehavior.STATE_EXPANDED
        }

        tvSubscribe.setOnClickListener {
            DebugUtils.debug(TAG, "inside tvSubscribe.setOnClickListener")
            bsmSubscribe.state = BottomSheetBehavior.STATE_HIDDEN
            updateBottomPurchase(mAdapter.getSelectedItem())
        }

        tvPaymentOption.setOnClickListener {
            showPopupMenu()
        }
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProviders.of(this)[SubscriptionViewModel::class.java]
    }

    private fun fetchSubscription() {
        if (Utils.isNetworkAvailable(this)) {
            mViewModel.fetchSubscription()
        } else {

        }
    }

    private fun showPopupMenu() {
        val popup = PopupMenu(this, tvPaymentOption)
        popup.menuInflater.inflate(R.menu.payment_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            /* Toast.makeText(
                 this,
                 "You Clicked : " + item.title,
                 Toast.LENGTH_SHORT
             ).show()*/
            tvPaymentOption.text = item.title
            true
        }
        popup.show()

    }

    private fun onListItemClicked(position: Int, item: DcbSubscription) {
        DebugUtils.debug(TAG, "onListItemClicked $position ${item.subscriptionAmount}")
        mAdapter.updateSelectedItem(position)
        updateBottomDetail(item)
    }

    private fun updateBottomDetail(item: DcbSubscription) {
        if (bsmPurchase.state != BottomSheetBehavior.STATE_HIDDEN) {
            bsmPurchase.state = BottomSheetBehavior.STATE_HIDDEN
        }
        bsmSubscribe.state = BottomSheetBehavior.STATE_EXPANDED
        tvSubSelected.text = item.subscriptionName
        tvSubCost.text = "₹ ${item.subscriptionAmount}"
    }

    private fun getPaymentMethods(): MutableList<String> {
        val list: MutableList<String> = arrayListOf()
        list.add("DCB")
        list.add("PayTm")
        list.add("Google Pay")
        list.add("Phone Pay")
        list.add("Debit Card")
        list.add("Credit Card")
        return list
    }

    private fun updateBottomPurchase(item: DcbSubscription) {
        bsmPurchase.state = BottomSheetBehavior.STATE_EXPANDED
        tvServiceSelected.text = item.subscriptionName
        tvServiceCost.text = "₹ ${item.subscriptionAmount}"
        tvPaymentOption.text = mPaymentMethods[0]
    }
}