package com.io.app.shakomako.ui.otp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.databinding.ActivityOtpBinding
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.HomeActivity
import com.io.app.shakomako.ui.login.LoginViewModel
import com.io.app.shakomako.ui.main.IntroActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant

class OtpActivity : DataBindingActivity<ActivityOtpBinding>(), ViewClickCallback {

    override fun layoutRes(): Int = R.layout.activity_otp
    var countDownTimer: CountDownTimer? = null
    lateinit var loginViewModel: LoginViewModel
    var isTimerStarted = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = bindViewModel {
            listener = this@OtpActivity
        }

        init()
    }

    private fun init() {
        startTimer(5000)
        loginViewModel.observer.completePhoneNumber =
            intent.getStringExtra(AppConstant.PARCEL_DATA) ?: ""
        dataBinding.tvPhoneNumber.text = loginViewModel.observer.completePhoneNumber
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_verify -> {
                if (isOtpValid()) {
                    loginViewModel.verifyOtp(
                        apiListener,
                        loginViewModel.observer.completePhoneNumber,
                        loginViewModel.observer.otp.toInt()
                    ).observe(this, verifyObserver)
                } else showToast("Not a Valid Otp")
            }
            R.id.ll_resend, R.id.resend -> {
                if (!isTimerStarted) {
                    startTimer(60000)
                    sendOtp(loginViewModel.observer.completePhoneNumber)
                }
            }
        }
    }

    private var verifyObserver: Observer<ApiResponse<TokenResponse>> =
        androidx.lifecycle.Observer { response ->

            when (response.status) {
                ApiConstant.NEW_USER_, ApiConstant.SUCCESS -> {

                    loginViewModel.userSession.createSession(
                        response.body?.token!!, true
                    )
                    startNewActivity(IntroActivity::class.java)
                    finishAffinity()
                }

                ApiConstant.ALREADY_REGISTERED -> {
                    loginViewModel.userSession.createSession(
                        response.body?.token!!, true
                    )
                    startNewActivity(HomeActivity::class.java)
                    finishAffinity()
                }

                else -> {
                    showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            }
        }

    private var apiListener: ApiListener = object : ApiListener {
        override fun showProgress(isVisible: Boolean) {
            if (isVisible) BaseUtils.showProgressbar(getThisActivity())
            else BaseUtils.hideProgressbar()
        }

        override fun msg(msg: String) {
            showToast(msg)
        }
    }

    private fun isOtpValid(): Boolean {
        if (dataBinding.otpView.text.toString()
                .isEmpty() || dataBinding.otpView.text?.length!! < 6
        ) {
            return false
        }
        loginViewModel.observer.otp = dataBinding.otpView.text.toString()
        return true
    }


    private fun startTimer(time: Int) {
        stopTimer()
        countDownTimer = object : CountDownTimer(time.toLong(), 1000) {
            override fun onTick(l: Long) {
                isTimerStarted = true
                dataBinding.resend.text = resources.getString(R.string.resend_in)
                dataBinding.count = ((l / 1000).toInt().toString())
            }

            override fun onFinish() {
                isTimerStarted = false
                dataBinding.resend.text = resources.getString(R.string.resend)
                dataBinding.count = ""
            }
        }
        (countDownTimer as CountDownTimer).start()
    }

    private fun stopTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            countDownTimer = null
        }
    }

    private fun sendOtp(phoneNumber: String) {
        loginViewModel.sendOtp(apiListener, phoneNumber).observe(this, Observer { response ->
            run {
                showToast(
                    response.message ?: resources.getString(R.string.msg_something_went_wrong)
                )
            }
        })
    }

}