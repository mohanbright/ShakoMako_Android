package com.io.app.shakomako.api.pojo.analytics

data class InsightData(
    var shopVisits: Int=0,
    var likes: Int=0,
    var totalShares: Int=0,
    var messageInitiations: Int=0,
    var totalFollowers: Int=0,
    var overallGrowth: Int=0,
    var followedYou : Int=0,
    var unfollowedYou: Int=0
)