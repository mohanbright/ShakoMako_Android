package com.io.app.shakomako.ui.product

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.io.app.shakomako.api.pojo.product.ProductRequest
import com.io.app.shakomako.api.pojo.shop.BusinessProducts
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.ui.home.HomeViewModel
import javax.inject.Inject

class AddProductViewModel @Inject constructor(context: Context, apiRepository: ApiRepository) :
    BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    var addProductObserver: AddProductObserver = AddProductObserver()
    var imageUploaded: MutableLiveData<Boolean> = MutableLiveData()


    inner class AddProductObserver : BaseObservable() {

        var type: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var businessId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var productRequest: ProductRequest = ProductRequest()
            set(value) {
                field = value
                notifyChange()
            }

        var businessProduct: BusinessProducts = BusinessProducts()
            set(value) {
                field = value
                notifyChange()
            }
    }

}