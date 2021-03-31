package com.io.app.shakomako.ui.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfileResponse
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.ChatMessageData
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.api.pojo.home.HomeData
import com.io.app.shakomako.api.pojo.login.LoginRequest
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.product.ProductRequest
import com.io.app.shakomako.api.pojo.product.ProductResponse
import com.io.app.shakomako.api.pojo.profile.ProfileResponse
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.api.pojo.shop.BusinessDetail
import com.io.app.shakomako.api.pojo.shop.BusinessProfile
import com.io.app.shakomako.api.pojo.upload.UploadResponse
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.utils.ApiUtils
import com.io.app.shakomako.utils.ContextUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.File

open class BaseViewModel : ViewModel() {
    protected lateinit var context: Context
    protected lateinit var apiRepository: ApiRepository


    fun login(
        listener: ApiListener,
        loginRequest: LoginRequest
    ): LiveData<ApiResponse<TokenResponse>> {
        val result: MutableLiveData<ApiResponse<TokenResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.login(loginRequest, object : Observer<ApiResponse<TokenResponse>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ApiResponse<TokenResponse>) {
                    listener.showProgress(false)
                    result.value = t
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage ?: "")
                }

                override fun onComplete() {

                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }

    fun sendOtp(listener: ApiListener, phoneNumber: String): LiveData<ApiResponse<Any>> {
        val result: MutableLiveData<ApiResponse<Any>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.sendOtp(phoneNumber, object : Observer<ApiResponse<Any>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ApiResponse<Any>) {
                    listener.showProgress(false)
                    result.value = t
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage ?: "")
                }

                override fun onComplete() {

                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }

    fun getUserProfile(listener: ApiListener): LiveData<ApiResponse<ProfileResponse>> {
        val result: MutableLiveData<ApiResponse<ProfileResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getUserProfile(object : Observer<ApiResponse<ProfileResponse>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ApiResponse<ProfileResponse>) {
                    listener.showProgress(false)
                    result.value = t
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage ?: "")
                }

                override fun onComplete() {

                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }


    fun getHomePageData(listener: ApiListener): LiveData<ApiResponse<HomeData>> {
        val result: MutableLiveData<ApiResponse<HomeData>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getHomePageData(object : Observer<ApiResponse<HomeData>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ApiResponse<HomeData>) {
                    listener.showProgress(false)
                    result.value = t
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage ?: "")
                }

                override fun onComplete() {

                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }


    fun getBusinessProfile(listener: ApiListener): LiveData<ApiResponse<BusinessProfile>> {
        val result: MutableLiveData<ApiResponse<BusinessProfile>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getBusinessProfile(object : Observer<ApiResponse<BusinessProfile>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ApiResponse<BusinessProfile>) {
                    listener.showProgress(false)
                    result.value = t
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage ?: "")
                }

                override fun onComplete() {

                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }

    fun getOtherBusinessProfile(
        businessId: Int,
        listener: ApiListener
    ): LiveData<ApiResponse<OtherBusinessProfileResponse>> {
        val result: MutableLiveData<ApiResponse<OtherBusinessProfileResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getOtherBusinessProfile(
                businessId,
                object : Observer<ApiResponse<OtherBusinessProfileResponse>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<OtherBusinessProfileResponse>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage ?: "")
                    }

                    override fun onComplete() {

                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }

    fun upload(listener: ApiListener, file: String): LiveData<ApiResponse<UploadResponse>> {
        val result: MutableLiveData<ApiResponse<UploadResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            val part = ContextUtils.uploadImage(File(file))
            apiRepository.upload(part, object : Observer<ApiResponse<UploadResponse>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ApiResponse<UploadResponse>) {
                    listener.showProgress(false)
                    result.value = t
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage ?: "")
                }

                override fun onComplete() {

                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }


    fun updateBusiness(
        businessDetail: BusinessDetail,
        listener: ApiListener
    ): LiveData<ApiResponse<BusinessDetail>> {
        val result: MutableLiveData<ApiResponse<BusinessDetail>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.updateBusiness(businessDetail,
                object : Observer<ApiResponse<BusinessDetail>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<BusinessDetail>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage ?: "")
                    }

                    override fun onComplete() {

                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }

    fun addProduct(
        productRequest: ProductRequest,
        listener: ApiListener
    ): LiveData<ApiResponse<Any>> {
        val result: MutableLiveData<ApiResponse<Any>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.addProduct(productRequest,
                object : Observer<ApiResponse<Any>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<Any>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage ?: "")
                    }

                    override fun onComplete() {

                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }
        return result
    }

    fun createUserName(userName: String): LiveData<ApiResponse<JsonObject>> {
        val username: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            apiRepository.createUserName(
                username = userName,
                observer = object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        Log.e("onNext", "${t.status}")
                        username.value = t
                    }

                    override fun onError(e: Throwable) {

                    }
                })


        } else {

        }


        return username
    }


    fun updateUserProfile(
        profileResponse: ProfileResponse,
        listener: ApiListener
    ): LiveData<ApiResponse<JsonObject>> {
        val updateProfileResponse: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()

        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.updateUserprofile(
                profileResponse,
                observer = object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        listener.showProgress(false)
                        Log.e("onNext", "${t.status}")
                        updateProfileResponse.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)

                    }
                })

        } else {
            listener.msg(context.getString(R.string.no_internet))

        }

        return updateProfileResponse
    }

    fun updateProduct(
        productRequest: ProductRequest,
        listener: ApiListener
    ): LiveData<ApiResponse<JsonObject>> {
        val updateProfileResponse: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()

        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.updateProduct(
                productRequest,
                observer = object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        listener.showProgress(false)
                        updateProfileResponse.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)

                    }
                })

        } else {
            listener.msg(context.getString(R.string.no_internet))

        }

        return updateProfileResponse
    }


    fun getBusinessChatList(listener: ApiListener): LiveData<List<BusinessChatResponse>> {
        val response: MutableLiveData<List<BusinessChatResponse>> = MutableLiveData()

        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getBusinessUserChatList(observer = object :
                Observer<ApiResponse<List<BusinessChatResponse>>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ApiResponse<List<BusinessChatResponse>>) {
                    listener.showProgress(false)
                    response.postValue(t.body)

                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage)
                }

            })


        } else {
            listener.msg(context.getString(R.string.no_internet))
        }




        return response


    }


    fun getpersonalChatList(listener: ApiListener): LiveData<List<PersonalChatResponse>> {
        val response: MutableLiveData<List<PersonalChatResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getPersonalUserChatList(observer = object :
                Observer<ApiResponse<List<PersonalChatResponse>>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: ApiResponse<List<PersonalChatResponse>>) {
                    listener.showProgress(false)
                    response.postValue(t.body)
                }

                override fun onError(e: Throwable) {
                    listener.showProgress(false)
                    listener.msg(e.localizedMessage)
                }
            })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return response
    }

    fun addDeliveryAddress(
        listener: ApiListener,
        deliveryAddress: DeliveryAddress
    ): LiveData<ApiResponse<JsonObject>> {
        val result: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.addDeliveryAddress(
                deliveryAddress,
                object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }

    fun updateDeliveryAddress(
        listener: ApiListener,
        deliveryAddress: DeliveryAddress
    ): LiveData<ApiResponse<JsonObject>> {
        val result: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.updateDeliveryAddress(
                deliveryAddress,
                object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }

    fun deleteDeliveryAddress(
        listener: ApiListener,
        id: Int
    ): LiveData<ApiResponse<JsonObject>> {
        val result: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.deleteDeliveryAddress(
                id,
                object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }

    fun getDeliveryAddress(
        listener: ApiListener
    ): LiveData<ApiResponse<List<DeliveryAddress>>> {
        val result: MutableLiveData<ApiResponse<List<DeliveryAddress>>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getDeliveryAddress(
                object : Observer<ApiResponse<List<DeliveryAddress>>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<List<DeliveryAddress>>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }

    fun getProductDetail(
        listener: ApiListener,
        id: Int
    ): LiveData<ApiResponse<ProductResponse>> {
        val result: MutableLiveData<ApiResponse<ProductResponse>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getProductDetail(
                id,
                object : Observer<ApiResponse<ProductResponse>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<ProductResponse>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }

    fun likeUnlikeProduct(
        listener: ApiListener,
        id: Int
    ): LiveData<ApiResponse<JsonObject>> {
        val result: MutableLiveData<ApiResponse<JsonObject>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.likeUnlikeProduct(
                id,
                object : Observer<ApiResponse<JsonObject>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<JsonObject>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }


    fun getAllChats(
        listener: ApiListener,
        roomId: Int
    ): LiveData<ApiResponse<List<ChatMessageData>>> {
        val result: MutableLiveData<ApiResponse<List<ChatMessageData>>> = MutableLiveData()
        if (ApiUtils.checkInternet(context)) {
            listener.showProgress(true)
            apiRepository.getAllChats(
                roomId,
                object : Observer<ApiResponse<List<ChatMessageData>>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ApiResponse<List<ChatMessageData>>) {
                        listener.showProgress(false)
                        result.value = t
                    }

                    override fun onError(e: Throwable) {
                        listener.showProgress(false)
                        listener.msg(e.localizedMessage)
                    }
                })
        } else {
            listener.msg(context.getString(R.string.no_internet))
        }

        return result
    }
}