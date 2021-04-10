package com.io.app.shakomako.api.pojo.recentproducts


data class RecentProducts(
    val avgRatings: String,
    val business_id: Int,
    val created_at: String,
    val product_asking_price: String,
    val product_category: String,
    val product_description: String,
    val product_hashtags: List<String>,
    val product_id: Int,
    val product_images: List<String>,
    val product_location: String,
    val user_id: Int
)
