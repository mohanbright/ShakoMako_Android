package com.io.app.shakomako.api.pojo.shop

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BusinessProfileDetails : BaseObservable() {

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

    @SerializedName("business_category")
    var businessCategory: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_location")
    var businessLocation: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_bio")
    var businessBio: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_picture")
    var businessPicture: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("businessVerificationStatus")
    var businessVerificationStatus: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("deals")
    var deals: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("followers")
    var followers: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("ratings")
    var ratings: String = ""
        set(value) {
            field = value
            notifyChange()
        }


}