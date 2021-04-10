package com.io.app.shakomako.api.pojo.shop

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.io.app.shakomako.api.pojo.business.OtherBusinessProduct

class BusinessProfile : BaseObservable() {

    @SerializedName("business")
    @Expose
    var businessProfileDetails: BusinessProfileDetails = BusinessProfileDetails()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("products")
    @Expose
    var businessProducts: List<BusinessProducts> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

}