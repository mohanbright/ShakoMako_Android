package com.io.app.shakomako.api.pojo.deal

data class PendingDealsResponse(
    val businessDeals: Int,
    val businessFollowing: Int,
    val businessRatings: String,
    val businessVerificationStatus: String,
    val business_id: Int,
    val business_name: String,
    val business_picture: String,
    val deal_amount: String,
    val deal_id: Int,
    val deal_status: String,
    val delivery_fee: String,
    val invoice_id: Int,
    val invoice_number: String,
    val product_id: Int,
    val product_images: String,
    val room_id: Int,
    val selfFollow: String,
    val total_amount: Int,
    val user_id: Int
)