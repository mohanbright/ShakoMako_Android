package com.io.app.shakomako.api.pojo.product

data class ProductRelatedResponse(

    var product_id: Int,
    var user_id: Int,
    var business_id: Int,
    var product_images: List<String>,
    var product_description: String,
    var product_hashtags: List<String>,
    var product_location: String,
    var product_category: String,
    var product_asking_price: String
)
