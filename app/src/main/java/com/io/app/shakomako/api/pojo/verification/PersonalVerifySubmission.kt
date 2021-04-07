package com.io.app.shakomako.api.pojo.verification

data class PersonalVerifySubmission(
    var customer_id: String = "",
    var personal_id: String = "",
    var shakomako_username: String = "",
    var user_email: String? = "",
    var user_name: String = "",
    var user_phone: String? = ""
)
