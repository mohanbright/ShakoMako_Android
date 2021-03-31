package com.io.app.shakomako.ui.login

import android.content.Context
import androidx.annotation.NonNull
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.ApiUtils
import com.io.app.shakomako.utils.session.UserSession
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    context: Context,
    apiRepository: ApiRepository,
    var userSession: UserSession
) :
    BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    val observer: LoginObserver = LoginObserver()

    fun verifyOtp(
        apiListener: ApiListener,
        phoneNumber: String,
        otp: Int
    ): LiveData<ApiResponse<TokenResponse>> {
        val result: MutableLiveData<ApiResponse<TokenResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            apiListener.showProgress(true)
            apiRepository.verifyOtp(
                phoneNumber,
                otp,
                object : Observer<ApiResponse<TokenResponse>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<TokenResponse>) {
                        apiListener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(@NonNull e: Throwable) {
                        apiListener.showProgress(false)
                        apiListener.msg(
                            e.localizedMessage
                                ?: context.getString(R.string.msg_something_went_wrong)
                        )
                    }

                    override fun onComplete() {

                    }
                })
        } else {
            apiListener.msg(context.getString(R.string.no_internet))
        }

        return result
    }

    inner class LoginObserver : BaseObservable() {
        var phoneNumber: String = ""
            set(value) {
                field = value
                notifyChange()
            }

        var countryCode: String = ""
            set(value) {
                field = value
                notifyChange()
            }

        var completePhoneNumber: String = ""
            set(value) {
                field = value
                notifyChange()
            }

        var otp: String = ""
            set(value) {
                field = value
                notifyChange()
            }
    }

}