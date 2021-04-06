package com.io.app.shakomako.api.pojo.like

import java.io.Serializable

data class LikedProductData(
    var product_asking_price: String = "",
    var product_id: Int = 0,
    var product_images: List<String> = ArrayList()
) : Serializable