package com.io.app.shakomako.api.pojo.analytics

data class Analytics(
    val businessVerificationStatus: String,
    val business_picture: String,
    val doneDeals: Int,
    val earnings: Any,
    val followers: Int,
    val pendingDeals: Int,
    val ratings: String,
    val totalDeals: Int
)