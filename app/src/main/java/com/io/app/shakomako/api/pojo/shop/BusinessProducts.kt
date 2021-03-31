package com.io.app.shakomako.api.pojo.shop

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BusinessProducts : BaseObservable(), Serializable {

    @SerializedName("product_id")
    @Expose
    var productId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_images")
    @Expose
    var productImages: List<String> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_description")
    @Expose
    var productDescription: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_hashtags")
    @Expose
    var productHashtags: List<String> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_category")
    var productCategory: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_asking_price")
    var productAskingPrice: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_location")
    var productLocation: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("selfLiked")
    var selfLiked: String = ""
        set(value) {
            field = value
            notifyChange()
        }

}