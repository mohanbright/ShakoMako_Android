package com.io.app.shakomako.api.pojo.shop

import android.text.TextUtils
import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.io.app.shakomako.api.exception.RequiredFieldExceptions

class BusinessDetail : BaseObservable() {

    @SerializedName("business_name")
    @Expose
    var businessName: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_category")
    @Expose
    var businessCategory: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_location")
    @Expose
    var businessLocation: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_bio")
    @Expose
    var businessBio: String = ""
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

    @Throws(RequiredFieldExceptions::class)
    fun isInputValid() {
        if (businessName.trim().isEmpty()) {
            throw RequiredFieldExceptions("Please Fill Business Name")
        } else if (businessCategory.trim().isEmpty()) {
            throw RequiredFieldExceptions("Please Select Business categories")
        } else if (businessLocation.trim().isEmpty()) {
            throw RequiredFieldExceptions("Please Fill Business Location")
        } else if (TextUtils.isEmpty(businessBio) || businessBio.trim().isEmpty()) {
            throw RequiredFieldExceptions("Please Fill Business Bio")
        } else if (TextUtils.isEmpty(businessPicture) || businessLocation.trim().isEmpty()) {
            throw RequiredFieldExceptions("Please Upload Bio Picture")
        }
    }
}