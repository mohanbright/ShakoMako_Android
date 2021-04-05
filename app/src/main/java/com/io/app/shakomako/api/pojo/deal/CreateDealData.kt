package com.io.app.shakomako.api.pojo.deal

import java.io.Serializable

data class CreateDealData(
    var business_id: Int = 0,
    var deal_amount: Int = 0,
    var invoice_number: String = "",
    var product_id: Int = 0,
    var room_id: Int = 0,
    var user_id: Int = 0
): Serializable