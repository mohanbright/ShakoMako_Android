package com.io.app.shakomako.api.pojo.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ApiResponse<T> : Parcelable {
    @SerializedName("status")
    var status: Int? = null

    @SerializedName("data")
    var body: T? = null
        private set

    @SerializedName("message")
    var message: String? = null

    constructor() {}
    protected constructor(`in`: Parcel) {
        status = if (`in`.readByte().toInt() == 0) {
            null
        } else {
            `in`.readInt()
        }
        message = `in`.readString()
    }

    fun setBody(body: T) {
        this.body = body
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        if (status == null) {
            parcel.writeByte(0.toByte())
        } else {
            parcel.writeByte(1.toByte())
            parcel.writeInt(status!!)
        }
        parcel.writeString(message)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ApiResponse<*>> =
            object : Parcelable.Creator<ApiResponse<*>> {
                override fun createFromParcel(`in`: Parcel): ApiResponse<*> {
                    return ApiResponse<Any?>(`in`)
                }

                override fun newArray(size: Int): Array<ApiResponse<*>?> {
                    return arrayOfNulls(size)
                }
            }
    }
}