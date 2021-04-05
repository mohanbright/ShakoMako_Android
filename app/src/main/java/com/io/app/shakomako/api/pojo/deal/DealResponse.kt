package com.io.app.shakomako.api.pojo.deal

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DealResponse : BaseObservable() {

    @SerializedName("deal_id")
    @Expose
    var dealId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }
}