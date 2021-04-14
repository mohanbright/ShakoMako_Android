package com.io.app.shakomako.api.pojo.analytics

data class Analytics(
    val businessVerificationStatus: String = "",
    val business_picture: String = "",
    val doneDeals: Int = 0,
    val earnings: Integer,
    val followers: Int = 0,
    val pendingDeals: Int = 0,
    val ratings: String = "",
    val totalDeals: Int = 0
)