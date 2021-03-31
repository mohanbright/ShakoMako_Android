package com.io.app.shakomako.api.pojo.product

import android.content.Context
import android.text.TextUtils
import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.io.app.shakomako.R
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import com.io.app.shakomako.api.pojo.shop.BusinessProducts

class ProductRequest : BaseObservable() {


    @SerializedName("product_description")
    @Expose
    var productDescription: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("business_id")
    @Expose
    var businessid: Int = 0
        set(value) {

            field = value
            notifyChange()
        }

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

    @SerializedName("product_hashtags")
    @Expose
    var productHashTags: List<String> = ArrayList()
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_location")
    @Expose
    var productLocation: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_category")
    @Expose
    var productCategory: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("product_asking_price")
    @Expose
    var productAskingPrice: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @Throws(RequiredFieldExceptions::class)
    fun isInputValid(context: Context, isUpdate: Boolean) {
        if (productImages.isEmpty() || productImages.size < 5) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_select_product_images))
        } else if (productDescription.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_product_description))
        } else if (productLocation.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fille_radius))
        } else if (TextUtils.isEmpty(productCategory) || productCategory.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_select_category))
        } else if (TextUtils.isEmpty(productAskingPrice) || productAskingPrice.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_asking_price))
        } else if (isUpdate) {
            if (productId == 0) {
                throw RequiredFieldExceptions(context.resources.getString(R.string.something_went_wrong_with_product_id))
            }
        } else {
            if (businessid == 0) {
                throw RequiredFieldExceptions(context.resources.getString(R.string.something_went_wrong_with_business_id))
            }
        }
    }


    fun setData(data: BusinessProducts) {
        productDescription = data.productDescription
        productAskingPrice = data.productAskingPrice
        productImages = data.productImages
        productHashTags = data.productHashtags
        productCategory = data.productCategory
        productLocation = data.productLocation
    }
}