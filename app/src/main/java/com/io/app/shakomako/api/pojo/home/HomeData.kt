package com.io.app.shakomako.api.pojo.home

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.io.app.shakomako.api.pojo.home.item.*
import java.io.Serializable


class HomeData : BaseObservable(), Serializable {

    @SerializedName("beauty")
    @Expose
    var beauty: List<HomeFashionData> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("fashion")
    @Expose
    var fashion: List<HomeFashionData> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("electronics")
    @Expose
    var electronics: List<HomeFashionData> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("travel")
    @Expose
    var travel: List<HomeFashionData> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("hotel")
    @Expose
    var hotel: List<HomeFashionData> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

}