package com.io.app.shakomako.api.pojo.invoice

import java.io.Serializable

data class InvoiceSubmitData(
    val customer_id: String = "",
    val deliveryAddress: String = "",
    val invoice_number: String = "",
    val product_asking_price: String = "",
    val product_id: Int = 0,
    val product_images: String = "",
    val shakomako_username: String = "",
    val user_name: String = ""
):Serializable