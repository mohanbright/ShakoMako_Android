package com.io.app.shakomako.api.pojo.login

import androidx.databinding.BaseObservable
import com.google.gson.annotations.SerializedName

class TokenResponse : BaseObservable() {
    @SerializedName("token")
    var token: String? = ""
        set(value) {
            field = value
            notifyChange()
        }
}