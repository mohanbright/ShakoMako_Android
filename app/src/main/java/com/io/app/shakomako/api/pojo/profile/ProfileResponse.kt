package com.io.app.shakomako.api.pojo.profile

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileResponse : BaseObservable() {

    @SerializedName("user_id")
    @Expose
    var userId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_id")
    @Expose
    var businessId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("user_name")
    @Expose
    var userName: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("user_email")
    @Expose
    var userEmail: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("user_phone")
    @Expose
    var userPhone: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("user_image")
    @Expose
    var userImage: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("user_gender")
    @Expose
    var userGender: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("login_type")
    @Expose
    var loginType: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("shakomako_username")
    @Expose
    var shakoMakoUserName: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("date_of_birth")
    @Expose
    var dateOfBirth: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("customer_id")
    @Expose
    var customerId: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("personVerification_status")
    @Expose
    var personVerificationStatus: String = ""
        set(value) {
            field = value
            notifyChange()
        }
}