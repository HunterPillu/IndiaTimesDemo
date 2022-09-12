package com.example.myapplication.view.activities


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.R
import com.example.myapplication.common_util.Utils
import com.example.myapplication.viewmodel.SplashViewModel
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import dev.shreyaspatil.MaterialDialog.model.TextAlignment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DcbBaseActivity() {
    private val TAG: String = HomeActivity::class.java.name
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mViewModel: SplashViewModel
    //private var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initToolbar(getString(R.string.title_my_radisys), null)
        initViews()
        //setUpViewModel()
       // observeLiveData()

    }

    private fun initViews() {
        cvRegistration.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        cvDetails.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private fun createNewSession() {
        if (Utils.isNetworkAvailable(this, false)) {
            /* mViewModel.createSession(
                 sharedPref.getString(PrefUtils.KEY_USERNAME, "") ?: "",
                 sharedPref.getString(PrefUtils.KEY_PASSWORD, "") ?: ""
             )*/
        } else {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        val mDialog = BottomSheetMaterialDialog.Builder(this)
            .setTitle(getString(R.string.title_no_internet), TextAlignment.START)
            .setMessage(getString(R.string.msg_no_internet), TextAlignment.START)
            .setCancelable(true)
            .setAnimation("no_internet.json")
            .setNegativeButton(
                getString(R.string.text_exit)
            ) { dialogInterface, which ->
                dialogInterface?.dismiss()
                finish()
            }.setPositiveButton(
                getString(R.string.text_retry)
            ) { dialogInterface, which ->
                dialogInterface?.dismiss()
                createNewSession()
            }
            .build()

        mDialog.show()

        val animationView = mDialog.animationView
        animationView.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }

    private fun observeLiveData() {
        /*mViewModel.sessionLiveData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    //pbProgress.visibility = View.INVISIBLE
                    val cookie = mViewModel.cookie
                    PreferenceManager.getDefaultSharedPreferences(applicationContext).edit()
                        .putString(PrefUtils.KEY_SESSION, Gson().toJson(it.data))
                        .putString(PrefUtils.KEY_COOKIE, cookie).apply()

                    DebugUtils.w(TAG, "cookie", cookie)
                    startActivity(
                        Intent(this@SplashActivity, MainActivity::class.java)
                            .putExtra(Constants.EXTRA_COOKIE, cookie)
                    )
                    finish()
                }


                else -> {}
            }
        }*/
    }

    private fun setUpViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

    }


}