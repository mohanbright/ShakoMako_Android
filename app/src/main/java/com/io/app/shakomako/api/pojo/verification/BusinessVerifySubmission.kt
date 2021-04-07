package com.io.app.shakomako.api.pojo.verification

data class BusinessVerifySubmission(
    var business_id: Int = 0,
    var shakomako_username: String = "",
    var deal_status: String = "",
    var personVerification_status: String = "",
    var vendor_id: String = ""
)
