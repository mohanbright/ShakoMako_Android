package com.io.app.shakomako.api.pojo.home.item

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class HomeFashionData : BaseObservable(), Serializable {
    @SerializedName("business_id")
    @Expose
    var businessId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_name")
    @Expose
    var businessName: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_picture")
    @Expose
    var businessPicture: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("businessVerificationStatus")
    @Expose
    var businessVerificationStatus: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("ratings")
    @Expose
    var ratings: String = ""
        set(value) {
            field = value
            notifyChange()
        }
}