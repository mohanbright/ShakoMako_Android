package com.io.app.shakomako.api.repo

import androidx.annotation.NonNull
import com.google.gson.JsonObject
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfileResponse
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.ChatMessageData
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.api.pojo.chatroom.ChatRoomData
import com.io.app.shakomako.api.pojo.deal.CreateDealData
import com.io.app.shakomako.api.pojo.deal.DealResponse
import com.io.app.shakomako.api.pojo.home.HomeData
import com.io.app.shakomako.api.pojo.invoice.*
import com.io.app.shakomako.api.pojo.login.LoginRequest
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.product.ProductRequest
import com.io.app.shakomako.api.pojo.product.ProductResponse
import com.io.app.shakomako.api.pojo.profile.ProfileResponse
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.api.pojo.shop.BusinessDetail
import com.io.app.shakomako.api.pojo.shop.BusinessProfile
import com.io.app.shakomako.api.pojo.upload.UploadResponse
import com.io.app.shakomako.api.rest.RestApi
import com.io.app.shakomako.utils.session.UserSession
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.Part
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var builder: Retrofit.Builder,
    private var okHttpBuilder: OkHttpClient.Builder,
    private var userSession: UserSession

) {
    private var headerLess: RestApi? = null
    private var withHeader: RestApi? = null

    private fun getHeaderLessApi(): RestApi? {
        if (headerLess == null) {
            headerLess = builder.build().create(RestApi::class.java)
        }
        return headerLess
    }

    private fun getHeaderApi(): RestApi? {
        if (withHeader == null) {
            okHttpBuilder
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder().addHeader(
                        "Authorization",
                        if (userSession.userToken == null) {
                            ""
                        } else {
                            userSession.userToken ?: ""
                        }
                    ).build()
                    chain.proceed(request)
                })

            withHeader = builder.client(okHttpBuilder.build()).build().create(RestApi::class.java)
        }

        return withHeader
    }

    /*--------------------------------------------- ------------- ----------------------------------------*/
    /*--------------------------------------------- Post Type Api ----------------------------------------*/
    /*--------------------------------------------- ------------- ----------------------------------------*/


    fun login(loginRequest: LoginRequest, @NonNull observer: Observer<ApiResponse<TokenResponse>>) {
        getHeaderLessApi()
            ?.login(loginRequest)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun sendOtp(phoneNumber: String, @NonNull observer: Observer<ApiResponse<Any>>) {
        getHeaderLessApi()
            ?.sendOtp(phoneNumber)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun verifyOtp(
        phoneNumber: String,
        otp: Int,
        @NonNull observer: Observer<ApiResponse<TokenResponse>>
    ) {
        getHeaderLessApi()
            ?.verifyOtp(phoneNumber, otp)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun upload(
        @Part imageFileBody: MultipartBody.Part,
        observer: Observer<ApiResponse<UploadResponse>>
    ) {
        getHeaderApi()
            ?.upload(imageFileBody)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun updateBusiness(
        @NonNull businessDetail: BusinessDetail,
        @NonNull observer: Observer<ApiResponse<BusinessDetail>>
    ) {
        getHeaderApi()
            ?.updateBusiness(businessDetail)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun addProduct(
        @NonNull productRequest: ProductRequest,
        @NonNull observer: Observer<ApiResponse<Any>>
    ) {
        getHeaderApi()
            ?.addProduct(productRequest)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun createUserName(username: String, observer: Observer<ApiResponse<JsonObject>>) {
        getHeaderApi()
            ?.createUserName(username)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }


    fun updateUserprofile(
        profileResponse: ProfileResponse,
        observer: Observer<ApiResponse<JsonObject>>
    ) {
        getHeaderApi()
            ?.updateProfile(profileResponse)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun updateProduct(productRequest: ProductRequest, observer: Observer<ApiResponse<JsonObject>>) {
        getHeaderApi()
            ?.updateProduct(productRequest)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun addDeliveryAddress(
        deliveryAddress: DeliveryAddress,
        observer: Observer<ApiResponse<JsonObject>>
    ) {
        getHeaderApi()
            ?.addDeliveryAddress(deliveryAddress)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun updateDeliveryAddress(
        deliveryAddress: DeliveryAddress,
        observer: Observer<ApiResponse<JsonObject>>
    ) {
        getHeaderApi()
            ?.updateDeliveryAddress(deliveryAddress)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun deleteDeliveryAddress(
        id: Int,
        observer: Observer<ApiResponse<JsonObject>>
    ) {
        getHeaderApi()
            ?.deleteDeliveryAddress(id)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun likeUnlikeProduct(
        id: Int,
        observer: Observer<ApiResponse<JsonObject>>
    ) {
        getHeaderApi()
            ?.likeUnlikeProduct(id)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun createChatRoom(
        userId: Int,
        businessId: Int,
        observer: Observer<ApiResponse<ChatRoomData>>
    ) {
        getHeaderApi()
            ?.createChatRoom(userId, businessId)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun saveInvoiceData(
        saveInvoiceData: SaveInvoiceData,
        observer: Observer<ApiResponse<SaveInvoiceResponse>>
    ) {
        getHeaderApi()
            ?.saveInvoiceData(saveInvoiceData)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun createDeal(
        createDealData: CreateDealData,
        observer: Observer<ApiResponse<DealResponse>>
    ) {
        getHeaderApi()
            ?.createDeal(createDealData)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }


    /*--------------------------------------------- ------------- ----------------------------------------*/
    /*--------------------------------------------- Get Type Api ----------------------------------------*/
    /*--------------------------------------------- ------------- ----------------------------------------*/


    fun getUserProfile(
        @NonNull observer: Observer<ApiResponse<ProfileResponse>>
    ) {
        getHeaderApi()
            ?.getUserProfile()
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getHomePageData(
        @NonNull observer: Observer<ApiResponse<HomeData>>
    ) {
        getHeaderApi()
            ?.getHomePageData()
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getBusinessProfile(
        @NonNull observer: Observer<ApiResponse<BusinessProfile>>
    ) {
        getHeaderApi()
            ?.getBusinessProfile()
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getOtherBusinessProfile(
        businessId: Int,
        @NonNull observer: Observer<ApiResponse<OtherBusinessProfileResponse>>
    ) {
        getHeaderApi()
            ?.getOtherBusinessProfile(businessId)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getBusinessUserChatList(observer: Observer<ApiResponse<List<BusinessChatResponse>>>) {
        getHeaderApi()?.getBusinessUsersChatList()?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe(observer)

    }

    fun getPersonalUserChatList(observer: Observer<ApiResponse<List<PersonalChatResponse>>>) {
        getHeaderApi()?.getPersonalUsersChatList()?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe(observer)

    }

    fun getDeliveryAddress(observer: Observer<ApiResponse<List<DeliveryAddress>>>) {
        getHeaderApi()?.getDeliveryAddress()?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribe(observer)

    }

    fun getProductDetail(id: Int, observer: Observer<ApiResponse<ProductResponse>>) {
        getHeaderApi()
            ?.getProductDetail(id)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getAllChats(roomId: Int, observer: Observer<ApiResponse<List<ChatMessageData>>>) {
        getHeaderApi()
            ?.getAllChats(roomId)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getChatInvoiceProduct(
        roomId: Int,
        observer: Observer<ApiResponse<List<ChatInvoiceProductData>>>
    ) {
        getHeaderApi()
            ?.getChatInvoiceProduct(roomId)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getInvoiceSubmitData(
        userId: Int,
        productId: Int,
        roomId: Int,
        observer: Observer<ApiResponse<InvoiceSubmitData>>
    ) {
        getHeaderApi()
            ?.getInvoiceSubmitData(userId, productId, roomId)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }

    fun getInvoiceById(
        invoiceId: Int,
        observer: Observer<ApiResponse<InvoiceData>>
    ) {
        getHeaderApi()
            ?.getInvoiceById(invoiceId)
            ?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(observer)
    }


}