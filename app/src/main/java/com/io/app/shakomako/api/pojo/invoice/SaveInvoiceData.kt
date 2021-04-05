package com.io.app.shakomako.api.pojo.invoice

import android.content.Context
import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import java.io.Serializable
import java.lang.Exception

class SaveInvoiceData : BaseObservable(), Serializable {

    @SerializedName("customer_id")
    @Expose
    var customerId: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("deal_price")
    @Expose
    var dealPrice: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("deliveryAddress")
    @Expose
    var deliveryAddress: String? = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("delivery_fee")
    @Expose
    var deliveryFee: String = "0"
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("invoice_number")
    @Expose
    var invoiceNumber: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("notes")
    @Expose
    var notes: String = ""
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
    var productImages: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("quantity")
    @Expose
    var quantity: Int = 1
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("shakomako_username")
    @Expose
    var shakomakoUsername: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("total_price")
    @Expose
    var totalPrice: String = ""
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

    @SerializedName("user_name")
    @Expose
    var userName: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    fun setData(data: InvoiceSubmitData) {
        customerId = data.customer_id
        dealPrice = data.product_asking_price
        deliveryAddress = data.deliveryAddress
        invoiceNumber = data.invoice_number
        productId = data.product_id
        productImages = data.product_images
        shakomakoUsername = data.shakomako_username
        userName = data.user_name
    }

    fun setData(data: InvoiceData) {
        customerId = data.customer_id
        dealPrice = data.deal_price
        deliveryAddress = try {
            data.deliveryAddress.split("&")[0]
        }catch (e:Exception){
            data.deliveryAddress
        }
        invoiceNumber = data.invoice_number
        productId = data.product_id
        productImages = data.product_images
        shakomakoUsername = data.shakomako_username
        userName = data.user_name
        userId = data.user_id
        quantity = data.quantity
        totalPrice = data.total_price
        notes = data.notes
        deliveryFee = data.delivery_fee
    }



    @Throws(RequiredFieldExceptions::class)
    fun isValid(context: Context) {
        if (deliveryAddress == null || deliveryAddress?.trim()?.isEmpty()!!) {
            throw RequiredFieldExceptions("Delivery Address Can't be empty")
        } else if (quantity == 0) {
            throw RequiredFieldExceptions("Quantity Can't be Zero")
        } else if (notes.trim().isEmpty()) {
            throw RequiredFieldExceptions("Notes Can't be empty")
        } else if (dealPrice.trim().isEmpty() || dealPrice.trim() == "0") {
            throw RequiredFieldExceptions("Please Fill valid deal price")
        } else if (deliveryFee.trim().isEmpty()) {
            throw RequiredFieldExceptions("Notes Can't be empty")
        }
    }
}

