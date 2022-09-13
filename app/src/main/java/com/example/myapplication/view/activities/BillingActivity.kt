package com.example.myapplication.view.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.common_util.DebugUtils
import com.example.myapplication.common_util.PrefUtils
import com.example.myapplication.common_util.Status
import com.example.myapplication.common_util.Utils
import com.example.myapplication.model.BillingResp
import com.example.myapplication.model.DcbTransaction
import com.example.myapplication.view.adapter.TransactionAdapter
import com.example.myapplication.viewmodel.BillingViewModel
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment
import kotlinx.android.synthetic.main.activity_billing.*


class BillingActivity : DcbBaseActivity() {
    private val TAG: String = BillingActivity::class.java.name
    private lateinit var mViewModel: BillingViewModel
    private lateinit var mAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billing)
        initToolbar(getString(R.string.title_transaction), PrefUtils.getPhoneNumber(this))
        initViews()
        setOnClickListeners()
        setUpViewModel()
        observeLiveData()
        observeApiError()
        fetchBillingDetails()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun observeLiveData() {
        mViewModel.statusLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {

                    pbProgress.visibility = View.VISIBLE

                }
                Status.SUCCESS -> {
                    //progress.dismiss()

                    if (it.data == 1 && mViewModel.mBillings != null) {
                        pbProgress.visibility = View.GONE
                        if (mViewModel.mBillings?.success == false) {
                            showHideList(false, mViewModel.mBillings?.message)
                        } else {
                            showHideList(true, null)
                            updateUI(mViewModel.mBillings!!)

                        }
                    }
                }
                Status.ERROR -> {
                    //progress.dismiss()
                    pbProgress.visibility = View.GONE

                    if (it.data == 1) {
                        showHideList(false,null)
                    }
                    showErrorDialog(it.data, null)
                }
                else -> {

                }
            }
        }
    }

    private fun updateUI(mBillings: BillingResp) {
        tvBillingDate.text = mBillings.updated_at
        tvBillingAmount.text = "₹ ${mBillings.current_billing_amount}"
        mAdapter.addList(mBillings.transactions!!)
    }

    private fun showHideList(canShow: Boolean, message: String?) {
        if (canShow) {
            rvList.visibility = View.VISIBLE
            tvMessage.visibility = View.GONE
        } else {
            rvList.visibility = View.GONE
            tvMessage.text = message ?: getString(R.string.msg_no_transaction_found)
            tvMessage.visibility = View.VISIBLE
        }
    }

    private fun observeApiError() {
        mViewModel.apiErrorLiveData.observe(this) {
            pbProgress.visibility = View.GONE
            showErrorDialog(it.code, it.message)
        }
    }

    private fun showErrorDialog(type: Int?, msg: String?) {
        var message = msg
        val animationJson: String
        if (type == 1) {
            //means authenticate error
            message = getString(R.string.error_subscription)
            animationJson = "forget_password_animation.json"
        } else if (type == 2) {
            message = getString(R.string.transaction_failed)
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

    private fun initViews() {
        mAdapter = TransactionAdapter(arrayListOf(), ::onListItemClicked)
        rvList.adapter = mAdapter
        /*progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(true) // disable dismiss by tapping outside of the dialog*/
    }

    private fun setOnClickListeners() {

    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProviders.of(this)[BillingViewModel::class.java]
    }

    private fun fetchBillingDetails() {
        if (Utils.isNetworkAvailable(this)) {
            mViewModel.fetchBillingDetails(PrefUtils.getPhoneNumber(this))
        } else {
            Utils.showNoInternetDialog(this)
        }
    }

    private fun onListItemClicked(position: Int, item: DcbTransaction) {
        DebugUtils.debug(TAG, "onListItemClicked $position ${item.transactionAmount}")
    }
}