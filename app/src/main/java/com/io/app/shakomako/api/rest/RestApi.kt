package com.io.app.shakomako.api.rest

import com.google.gson.JsonObject
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.api.pojo.analytics.Analytics
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfileResponse
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.ChatMessageData
import com.io.app.shakomako.api.pojo.chat_response.OpenDealsData
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.api.pojo.chatroom.ChatRoomData
import com.io.app.shakomako.api.pojo.codapproval.CodApprovalData
import com.io.app.shakomako.api.pojo.deal.CreateDealData
import com.io.app.shakomako.api.pojo.deal.DealResponse
import com.io.app.shakomako.api.pojo.deal.PendingDealsResponse
import com.io.app.shakomako.api.pojo.home.HomeData
import com.io.app.shakomako.api.pojo.invoice.*
import com.io.app.shakomako.api.pojo.like.LikedBusinessData
import com.io.app.shakomako.api.pojo.like.LikedProductData
import com.io.app.shakomako.api.pojo.login.LoginRequest
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.product.ProductRelatedResponse
import com.io.app.shakomako.api.pojo.product.ProductRequest
import com.io.app.shakomako.api.pojo.product.ProductResponse
import com.io.app.shakomako.api.pojo.profile.ProfileResponse
import com.io.app.shakomako.api.pojo.recentproducts.RecentProducts
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.api.pojo.search.SearchQueryResponse
import com.io.app.shakomako.api.pojo.shop.BusinessDetail
import com.io.app.shakomako.api.pojo.shop.BusinessProfile
import com.io.app.shakomako.api.pojo.upload.UploadResponse
import com.io.app.shakomako.api.pojo.verification.BusinessVerificationData
import com.io.app.shakomako.api.pojo.verification.BusinessVerifySubmission
import com.io.app.shakomako.api.pojo.verification.PersonalVerificationData
import com.io.app.shakomako.api.pojo.verification.PersonalVerifySubmission
import com.io.app.shakomako.api.recentbusiness.RecentBusiness
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

    @FormUrlEncoded
    @POST("api/createChatRoom")
    fun createChatRoom(
        @Field("user_id") userId: Int,
        @Field("business_id") businessId: Int
    ): Observable<ApiResponse<ChatRoomData>>

    @POST("api/saveInvoiceData")
    fun saveInvoiceData(@Body saveInvoiceData: SaveInvoiceData): Observable<ApiResponse<SaveInvoiceResponse>>

    @POST("api/createDeal")
    fun createDeal(@Body createDealData: CreateDealData): Observable<ApiResponse<DealResponse>>

    @POST("api/codApproval")
    fun codApproval(@Body codeApprovalData: CodApprovalData): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/followUnfollowBusiness")
    fun businessFollow(
        @Field("business_id") businessId: Int
    ): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/businessVerifySubmission")
    fun businessVerifySubmission(@Field("business_id") businessId: Int): Observable<ApiResponse<JsonObject>>

    @POST("api/userVerifySubmission")
    fun userVerifySubmission(@Body data: PersonalVerifySubmission): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/createProductRating")
    fun createProductRating(
        @Field("product_id") productId: Int,
        @Field("rating") rating: String
    ): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/createBusinessRating")
    fun createBusinessRating(
        @Field("business_id") businessId: Int,
        @Field("rating") rating: String
    ): Observable<ApiResponse<JsonObject>>

    @FormUrlEncoded
    @POST("api/createUserRating")
    fun createUserRating(
        @Field("user_id") businessId: Int,
        @Field("rating") rating: String
    ): Observable<ApiResponse<JsonObject>>

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

    @GET("api/getChatInvoiceProduct")
    fun getChatInvoiceProduct(@Query("room_id") roomId: Int): Observable<ApiResponse<List<ChatInvoiceProductData>>>

    @GET("api/getInvoiceSubmitData")
    fun getInvoiceSubmitData(
        @Query("user_id") userId: Int,
        @Query("product_id") productId: Int,
        @Query("room_id") roomId: Int
    ): Observable<ApiResponse<InvoiceSubmitData>>

    @GET("api/getInvoiceById")
    fun getInvoiceById(
        @Query("invoice_id") invoiceId: Int
    ): Observable<ApiResponse<InvoiceData>>

    @GET("api/getLatestOpenDeals")
    fun getLatestOpenDeals(
        @Query("room_id") roomId: Int,
        @Query("getFor") getFor: String
    ): Observable<ApiResponse<List<OpenDealsData>>>

    @GET("api/getPersonalDeals")
    fun getDealsPersonal(
        @Query("deal_status") dealStatus: String
    ): Observable<ApiResponse<List<PendingDealsResponse>>>


    @GET("api/getPersonalDeals")
    fun getBusinessDeals(
        @Query("deal_status") dealStatus: String
    ): Observable<ApiResponse<List<PendingDealsResponse>>>

    @GET("api/getLikedProducts")
    fun getLikedProducts(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<ApiResponse<List<LikedProductData>>>


    @GET("api/getLikedBusiness")
    fun getLikedBusiness(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<ApiResponse<List<LikedBusinessData>>>

    @GET("api/getBusinessVerificationData")
    fun getBusinessVerificationData(): Observable<ApiResponse<BusinessVerificationData>>

    @GET("api/getpersonalVerificationData")
    fun getPersonalVerificationData(): Observable<ApiResponse<PersonalVerificationData>>

    @GET("api/getNotifications")
    fun getNotification(): Observable<ApiResponse<JsonObject>>

    @GET("api/getRelatedProducts")
    fun getRelatedProducts(
        @Query("product_category") product_category: String, @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<ApiResponse<List<ProductRelatedResponse>>>

    @GET("api/getAnalytics")
    fun getAnalytics(): Observable<ApiResponse<Analytics>>

    @GET("api/getRecentProducts")
    fun getRecentProducts(): Observable<ApiResponse<List<RecentProducts>>>


    @GET("api/getRecentBusiness")
    fun getRecentBusiness(): Observable<ApiResponse<List<RecentBusiness>>>

    @GET("api/universalSearchBar")
    fun setByQuery(
        @Query("search") product_category: String
    ): Observable<ApiResponse<List<SearchQueryResponse>>>

}