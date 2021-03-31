package com.io.app.shakomako.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.hbb20.CountryCodePicker
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.login.LoginRequest
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.databinding.ActivityLoginWithNumberBinding
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.BaseUtils.hideProgressbar
import com.io.app.shakomako.ui.base.BaseUtils.showProgressbar
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.HomeActivity
import com.io.app.shakomako.ui.otp.OtpActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant

class LoginActivity : DataBindingActivity<ActivityLoginWithNumberBinding>(), ViewClickCallback {
    override fun layoutRes(): Int = R.layout.activity_login_with_number

    lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = bindViewModel {
            ccp.changeDefaultLanguage(CountryCodePicker.Language.ARABIC)
        }

        init()
    }

    private fun init() {
        dataBinding.viewHandler = this
        dataBinding.viewModel = viewModel
        viewModel.observer.countryCode = "+" + dataBinding.ccp.selectedCountryCode
        dataBinding.ccp.setOnCountryChangeListener {
            viewModel.observer.countryCode = "+" + dataBinding.ccp.selectedCountryCode
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_sms_verification -> {
                if (viewModel.observer.phoneNumber.isEmpty()) {
                    showToast("Please Enter Valid Mobile Number")
                    return
                }
                val request = LoginRequest()
                request.userPhone = viewModel.observer.countryCode + viewModel.observer.phoneNumber
                request.loginType = "phone"
                viewModel.login(apiListener, request)
                    .observe(this, loginObserver)

            }
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    private fun sendOtp(phoneNumber: String) {
        viewModel.sendOtp(apiListener, phoneNumber).observe(this, Observer { response ->
            run {
                if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                    startActivity(
                        Intent(applicationContext, OtpActivity::class.java).putExtra(
                            AppConstant.PARCEL_DATA,
                            viewModel.observer.countryCode + viewModel.observer.phoneNumber
                        )
                    )
                } else {
                    showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            }
        })
    }

    private var apiListener: ApiListener = object : ApiListener {
        override fun showProgress(isVisible: Boolean) {
            if (isVisible) showProgressbar(getThisActivity())
            else hideProgressbar()
        }

        override fun msg(msg: String) {
            showToast(msg)
        }
    }

    private var loginObserver: Observer<ApiResponse<TokenResponse>> =
        Observer { response ->

            when (response.status) {
                ApiConstant.NEW_USER -> {
                    sendOtp(viewModel.observer.countryCode + viewModel.observer.phoneNumber)
                }

                ApiConstant.NEW_USER_ -> {
                    sendOtp(viewModel.observer.countryCode + viewModel.observer.phoneNumber)
                }

                ApiConstant.ALREADY_REGISTERED -> {
                    startActivity(
                        Intent(applicationContext, OtpActivity::class.java).putExtra(
                            AppConstant.PARCEL_DATA,
                            viewModel.observer.countryCode + viewModel.observer.phoneNumber
                        )
                    )
                }

                else -> {
                    showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            }
        }


}