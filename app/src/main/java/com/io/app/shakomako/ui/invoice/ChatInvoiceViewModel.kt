package com.io.app.shakomako.ui.invoice

import android.content.Context
import androidx.databinding.BaseObservable
import com.io.app.shakomako.api.pojo.invoice.InvoiceData
import com.io.app.shakomako.api.pojo.invoice.InvoiceSubmitData
import com.io.app.shakomako.api.pojo.invoice.SaveInvoiceData
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.session.SessionConstants
import com.io.app.shakomako.utils.session.UserSession
import javax.inject.Inject

class ChatInvoiceViewModel @Inject constructor(
    context: Context,
    apiRepository: ApiRepository,
    var userSession: UserSession
) : BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    var observer: ChatInvoiceObserver = ChatInvoiceObserver()

    fun setTotalDealPrice() {
        observer.saveInvoiceData.totalPrice = calculateTotalDealPrice().toString()
    }

    private fun calculateTotalDealPrice(): Int {
        return (observer.saveInvoiceData.quantity * ((observer.saveInvoiceData.dealPrice).toInt() + (observer.saveInvoiceData.deliveryFee).toInt()))
    }

    inner class ChatInvoiceObserver : BaseObservable() {

        var type: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var roomId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var businessId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var invoiceId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var userId = userSession.getIntValue(SessionConstants.USER_ID)

        var saveInvoiceData: SaveInvoiceData = SaveInvoiceData()

        var invoiceData: InvoiceSubmitData = InvoiceSubmitData()
            set(value) {
                field = value
                notifyChange()
            }

        var data: InvoiceData = InvoiceData()
            set(value) {
                field = value
                notifyChange()
            }
    }
}