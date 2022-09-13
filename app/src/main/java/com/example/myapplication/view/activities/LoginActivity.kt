package com.example.myapplication.view.activities

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.example.myapplication.R
import com.example.myapplication.common_util.DebugUtils
import com.example.myapplication.common_util.PrefUtils
import com.example.myapplication.common_util.Status
import com.example.myapplication.common_util.Utils
import com.example.myapplication.viewmodel.LoginViewModel
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : DcbBaseActivity() {
    private val TAG: String = "LoginActivity"
    private lateinit var mViewModel: LoginViewModel

    private var sharedPref: SharedPreferences? = null
    private lateinit var progress: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initToolbar(getString(R.string.title_my_radisys), null)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        initViews()
        setUpViewModel()
        observeLiveData()
        observeApiError()
    }

    override fun onBackPressed() {
        finish()
    }

    private fun observeLiveData() {
        mViewModel.statusLiveData.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    progress.show()
                }
                Status.SUCCESS -> {
                    progress.dismiss()

                    if (it.data == 2 && null != mViewModel.responseOTP) {
                        val result = mViewModel.responseOTP!!

                        PrefUtils.putBoolean(this, PrefUtils.KEY_LOGGED_IN, false)
                        PrefUtils.putString(this, PrefUtils.KEY_PHONE, etPhone.text.toString())
                        PrefUtils.putString(this, PrefUtils.KEY_EMAIL, etEmail.text.toString())

                        PrefUtils.putString(
                            this,
                            PrefUtils.KEY_ASS_ID,
                            mViewModel.responseDCB?.associationId
                        )
                        PrefUtils.putString(
                            this,
                            PrefUtils.KEY_REQ_ID,
                            mViewModel.responseDCB?.requestId
                        )
                        PrefUtils.putString(
                            this,
                            PrefUtils.KEY_GP_TOKEN,
                            mViewModel.responseDCB?.gpToken
                        )

                        if (result.success == true) {
                            startActivity(Intent(this, BillingActivity::class.java))
                            finish()
                        } else {
                            showErrorDialog(it.data, result.message)
                        }

                    } else if (it.data == 1 && null != mViewModel.responseDCB) {

                        //PrefUtils.removeLoginDataIfExist(this)

                        PrefUtils.putBoolean(
                            this,
                            PrefUtils.KEY_REMEMBER_ME,
                            cbRememberMe.isChecked
                        )
                        PrefUtils.putString(
                            this,
                            PrefUtils.KEY_EMAIL_CACHE,
                            etEmail.text.toString()
                        )

                        PrefUtils.putString(
                            this,
                            PrefUtils.KEY_PHONE_CACHE,
                            etPhone.text.toString()
                        )


                        //val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                        //loginIntent.putExtra(Constants.EXTRA_FROM, "login")
                        //startActivity(loginIntent)
                        //finish()

                        llLogin.visibility = View.GONE
                        llOtp.visibility = View.VISIBLE

                    }
                }
                Status.ERROR -> {
                    progress.dismiss()
                    DebugUtils.debug(TAG, "${it.data}")
                    DebugUtils.debug(TAG, "${it.message}")
                    showErrorDialog(it.data, null)
                }
                else -> {

                }
            }
        }
    }

    private fun observeApiError() {
        mViewModel.apiErrorLiveData.observe(this) {
            progress.dismiss()
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
            message = mViewModel.responseOTP?.message ?: getString(R.string.something_went_wrong)
            animationJson = "forget_password_animation.json"
        } else {
            //something else
            message = msg ?: getString(R.string.something_went_wrong)
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
        if (PrefUtils.getBoolean(this, PrefUtils.KEY_REMEMBER_ME)) {
            cbRememberMe.isChecked = true
            etEmail.setText(PrefUtils.getString(this, PrefUtils.KEY_EMAIL_CACHE))
            etPhone.setText(PrefUtils.getString(this, PrefUtils.KEY_PHONE_CACHE))
        }

        setOnClickListeners()
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(true) // disable dismiss by tapping outside of the dialog
    }

    private fun setOnClickListeners() {
        tvResendOtp.setOnClickListener {
            initAuthenticate()
        }

        tv_login.setOnClickListener { view: View? ->
            if (etEmail.text.toString().trim() == "") {
                input_email.error = "Please input valid username"
                etEmail.requestFocus()
            } else if (etPhone.text.toString().trim().length != 10) {
                input_phone.error = "Please input valid password"
                etPhone.requestFocus()
            } else {
                initAuthenticate()
            }
        }

        tvAssociation.setOnClickListener {
            if (etOTP.text.toString().trim() == "") {
                input_otp.error = "Please enter OTP"
                etOTP.requestFocus()
            } else if (etOTP.text.toString().trim().length != 6) {
                input_otp.error = "Please input valid OTP"
                etOTP.requestFocus()
            } else {
                initAssociate()
            }
        }
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProviders.of(this)[LoginViewModel::class.java]
    }

    private fun initAuthenticate() {
        if (Utils.isNetworkAvailable(this)) {
            mViewModel.initAuthenticate(etEmail.text.toString(), etPhone.text.toString())
        }
    }

    private fun initAssociate() {
        if (Utils.isNetworkAvailable(this)) {
            mViewModel.initAssociate(etOTP.text.toString())
        }
    }
}