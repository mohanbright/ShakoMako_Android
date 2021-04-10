package com.io.app.shakomako.api.pojo.like

import java.io.Serializable

data class LikedBusinessData(
    var businessVerificationStatus: String = "",
    var business_name: String = "",
    var business_picture: String = ""
) : Serializable