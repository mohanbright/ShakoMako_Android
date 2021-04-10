package com.io.app.shakomako.api.pojo.login

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest : BaseObservable() {

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

    /**
     * @param login_type -  instagram, facebook, google, apple, phone
     * */
    @SerializedName("login_type")
    @Expose
    var loginType: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("social_key")
    @Expose
    var socialKey: String = ""
        set(value) {
            field = value
            notifyChange()
        }
}