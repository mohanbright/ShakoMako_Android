package com.io.app.shakomako.api.pojo.product

import java.io.Serializable

data class ProductResponse(
    val avgRatings: String = "",
    val business_id: Int = 0,
    val created_at: String = "",
    val product_asking_price: String = "",
    val product_category: String = "",
    val product_description: String = "",
    val product_hashtags: List<String> = ArrayList(),
    val product_id: Int = 0,
    val product_images: List<String> = ArrayList(),
    val product_location: String = "",
    val selfLiked: String = "",
    val total1Stars: Int = 0,
    val total2Stars: Int = 0,
    val total3Stars: Int = 0,
    val total4Stars: Int = 0,
    val total5Stars: Int = 0,
    val user_id: Int = 0
):Serializable