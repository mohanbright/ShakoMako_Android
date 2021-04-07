package com.io.app.shakomako.api.pojo.verification

import java.io.Serializable

data class BusinessVerificationData(
    var business_name: String = "",
    var business_picture: String = "",
    var dealStatus: String = "",
    var followers: Int = 0,
    var personVerification_status: String = "",
    var ratings: String = "",
    var totalDeals: Int = 0,
    var venderId: String = ""
) : Serializable