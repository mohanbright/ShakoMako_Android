package com.io.app.shakomako.api.pojo.verification

import java.io.Serializable

data class PersonalVerificationData(
    var customer_id: String = "",
    var deals: Int = 0,
    var doneDeals: Int = 0,
    var following: Int = 0,
    var national_id: String? = "",
    var ratings: Int = 0,
    var shakomako_username: String = "",
    var user_email: String? = "",
    var user_name: String = "",
    var user_phone: String? = ""
) : Serializable