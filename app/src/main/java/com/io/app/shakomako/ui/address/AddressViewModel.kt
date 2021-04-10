package com.io.app.shakomako.ui.address

import android.content.Context
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import javax.inject.Inject

class AddressViewModel @Inject constructor(context: Context, apiRepository: ApiRepository) :
    BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    var deliveryAddress: DeliveryAddress = DeliveryAddress()

}