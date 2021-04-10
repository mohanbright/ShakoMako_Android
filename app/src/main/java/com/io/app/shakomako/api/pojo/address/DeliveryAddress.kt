package com.io.app.shakomako.api.pojo.address

import android.content.Context
import android.text.TextUtils
import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.io.app.shakomako.R
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import java.io.Serializable

class DeliveryAddress : BaseObservable(), Serializable {


    @SerializedName("address_id")
    @Expose
    var addressId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("user_id")
    @Expose
    var userId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("label")
    @Expose
    var label: String = ""
        set(value) {
            field = value
            notifyChange()
        }


    @SerializedName("building_number")
    @Expose
    var buildingNumber: String = ""
        set(value) {
            field = value
            notifyChange()
        }


    @SerializedName("street_name")
    @Expose
    var streetName: String = ""
        set(value) {
            field = value
            notifyChange()
        }


    @SerializedName("district")
    @Expose
    var district: String = ""
        set(value) {
            field = value
            notifyChange()
        }


    @SerializedName("city")
    @Expose
    var city: String = ""
        set(value) {
            field = value
            notifyChange()
        }


    @SerializedName("country")
    @Expose
    var country: String = ""
        set(value) {
            field = value
            notifyChange()
        }


    @Throws(RequiredFieldExceptions::class)
    fun isInputValid(context: Context) {
        if (label.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_lable))
        } else if (buildingNumber.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_building_number))
        } else if (TextUtils.isEmpty(streetName) || streetName.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_streetname))
        } else if (TextUtils.isEmpty(district) || district.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_district))
        } else if (TextUtils.isEmpty(city) || city.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_city))
        } else if (TextUtils.isEmpty(country) || country.trim().isEmpty()) {
            throw RequiredFieldExceptions(context.resources.getString(R.string.please_fill_country))
        }
    }
}