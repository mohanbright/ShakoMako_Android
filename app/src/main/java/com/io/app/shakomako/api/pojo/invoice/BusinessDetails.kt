package com.io.app.shakomako.api.pojo.invoice

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BusinessDetails(
    var business_id: Int = 0,
    var business_name: String = "",
    var business_picture: String = "",
    var deals: Int = 0,
    var followers: Int = 0,
    var ratings: String = "",
    var selfFollow: String = ""
):Serializable