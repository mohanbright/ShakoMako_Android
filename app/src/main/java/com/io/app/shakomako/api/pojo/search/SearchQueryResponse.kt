package com.io.app.shakomako.api.pojo.search


data class SearchQueryResponse(
    val product_asking_price: String,
    val product_category: String,
    val product_description: String,
    val product_hashtags: List<Any>,
    val product_id: Int,
    val product_images: List<String>,
    val ratings: String
)