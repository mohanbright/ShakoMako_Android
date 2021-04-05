package com.io.app.shakomako.api.pojo.invoice

import java.io.Serializable

data class InvoiceData(
    var businessDetails: BusinessDetails = BusinessDetails(),
    var business_id: Int = 0,
    var customer_id: String = "",
    var deal_price: String = "",
    var deliveryAddress: String = "",
    var delivery_fee: String = "",
    var invoice_id: Int = 0,
    var invoice_number: String = "",
    var notes: String = "",
    var product_id: Int = 0,
    var product_images: String = "",
    var quantity: Int = 0,
    var shakomako_username: String = "",
    var total_price: String = "",
    var user_id: Int = 0,
    var user_name: String = ""
) : Serializable