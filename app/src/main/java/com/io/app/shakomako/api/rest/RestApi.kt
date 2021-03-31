package com.io.app.shakomako.api.rest

import com.google.gson.JsonObject
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
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface RestApi {

    /** POST APIS*/

    @POST("api/login")
    fun login(@Body loginRequest: LoginRequest): Observable<ApiResponse<TokenResponse>>

    @FormUrlEncoded
    @POST("api/sendOtp")
    fun sendOtp(@Field("user_phone") phoneNumber: String): Observable<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("api/verifyOtp")
    fun verifyOtp(
        @Field("user_phone") phoneNumber: String,
        @Field("otp") otp: Int
    ): Observable<ApiResponse<TokenResponse>>

    @Multipart
    @POST("api/upload")
    fun upload(@Part imageBody: MultipartBody.Part): Observable<ApiResponse<UploadResponse>>

    @POST("api/addUpdateBusiness")
    fun updateBusiness(@Body businessDetail: BusinessDetail): Observable<ApiResponse<BusinessDetail>>

    @POST("api/addProduct")
    fun addProduct(@Body productRequest: ProductRequest): Observable<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("api/createShakomakoUsername")
    fun createUserName(@Field("shakomako_username") shakoMakoUserName: String): Observable<ApiResponse<JsonObject>>


    @POST("api/updateUserProfile")
    fun updateProfile(@Body profileDetail: ProfileResponse): Observable<ApiResponse<JsonObject>>

    @POST("api/updateProduct")
    fun updateProduct(@Body productRequest: ProductRequest): Observable<ApiResponse<JsonObject>>

    @POST("api/addDeliveryAddress")
    fun addDeliveryAddress(@Body deliveryAddress: DeliveryAddress): Observable<ApiResponse<JsonObject>>

    @POST("api/updateDeliveryAddress")
    fun updateDeliveryAddress(@Body deliveryAddress: DeliveryAddress): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/deleteDeliveryAddress")
    fun deleteDeliveryAddress(@Field("address_id") id: Int): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/likeUnlikeProduct")
    fun likeUnlikeProduct(@Field("product_id") id: Int): Observable<ApiResponse<JsonObject>>


    /** GET APIS*/

    @GET("api/getUserProfile")
    fun getUserProfile(): Observable<ApiResponse<ProfileResponse>>

    @GET("api/getHomepageList")
    fun getHomePageData(): Observable<ApiResponse<HomeData>>

    @GET("api/getBusinessProfile")
    fun getBusinessProfile(): Observable<ApiResponse<BusinessProfile>>

    @GET("api/getBusinessProfileById")
    fun getOtherBusinessProfile(@Query("business_id") businessId: Int): Observable<ApiResponse<OtherBusinessProfileResponse>>

    @GET("api/getBusinessChatList")
    fun getBusinessUsersChatList(): Observable<ApiResponse<List<BusinessChatResponse>>>


    @GET("api/getPersonalChatList")
    fun getPersonalUsersChatList(): Observable<ApiResponse<List<PersonalChatResponse>>>

    @GET("api/getDeliveryAddress")
    fun getDeliveryAddress(): Observable<ApiResponse<List<DeliveryAddress>>>

    @GET("api/getProductById")
    fun getProductDetail(@Query("product_id") id: Int): Observable<ApiResponse<ProductResponse>>

    @GET("api/getAllChats")
    fun getAllChats(@Query("room_id") roomId: Int): Observable<ApiResponse<List<ChatMessageData>>>

}