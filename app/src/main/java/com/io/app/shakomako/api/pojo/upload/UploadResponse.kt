package com.io.app.shakomako.api.pojo.upload

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadResponse : BaseObservable() {

    @SerializedName("image")
    @Expose
    var image: String = ""
        set(value) {
            field = value
            notifyChange()
        }
}