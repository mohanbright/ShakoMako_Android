package com.io.app.shakomako.api.pojo.chat_response

import java.io.Serializable

data class OpenDealsData(
    var business_id: Int = 0,
    var created_at: String = "",
    var deal_amount: String = "",
    var deal_id: Int = 0,
    var deal_status: String = "",
    var invoice_number: String = "",
    var product_id: Int = 0,
    var room_id: Int = 0,
    var total_amount: Int = 0,
    var user_id: Int = 0
) : Serializable