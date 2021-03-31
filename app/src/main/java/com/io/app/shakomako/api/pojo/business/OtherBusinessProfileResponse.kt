package com.io.app.shakomako.api.pojo.business

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OtherBusinessProfileResponse : BaseObservable(), Serializable {

    @SerializedName("business")
    @Expose
    var businessProfile: OtherBusinessProfile = OtherBusinessProfile()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("products")
    @Expose
    var businessProducts: List<OtherBusinessProduct> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }
}