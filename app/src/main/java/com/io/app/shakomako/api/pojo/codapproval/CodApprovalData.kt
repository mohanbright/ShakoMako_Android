package com.io.app.shakomako.api.pojo.codapproval

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CodApprovalData : BaseObservable() {


//    @SerializedName("invoice_id")
//    @Expose
//    var invoiceId: Int = 0
//        set(value) {
//            field = value
//            notifyChange()
//        }

    @SerializedName("deal_id")
    @Expose
    var dealId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("type")
    @Expose
    var type: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @SerializedName("status")
    @Expose
    var status: String = "yes"
        set(value) {
            field = value
            notifyChange()
        }
}