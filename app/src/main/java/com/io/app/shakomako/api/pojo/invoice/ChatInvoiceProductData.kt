package com.io.app.shakomako.api.pojo.invoice

import java.io.Serializable

data class ChatInvoiceProductData(
    val product_asking_price: String = "",
    val product_id: Int = 0,
    val product_images: String = ""
) : Serializable