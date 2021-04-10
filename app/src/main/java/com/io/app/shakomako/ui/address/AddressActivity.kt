package com.io.app.shakomako.ui.address

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.databinding.ActivityAddressBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import kotlinx.android.synthetic.main.activity_home.*

class AddressActivity : DataBindingActivity<ActivityAddressBinding>(), ViewClickCallback {

    lateinit var viewModel: AddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(AddressViewModel::class.java)
        init()

    }


    private fun init() {
        dataBinding.viewHandler = this
        dataBinding.viewModel = viewModel

        viewModel.deliveryAddress =
            intent.getSerializableExtra(AppConstant.PARCEL_DATA) as DeliveryAddress
    }

    override fun layoutRes(): Int {
        return R.layout.activity_address
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                finish()
            }

            R.id.tv_save_address -> {

                if (intent.getIntExtra(AppConstant.TYPE, 0) == AppConstant.ADD_ADDRESS)
                    try {
                        viewModel.deliveryAddress.isInputValid(this@AddressActivity)
                        viewModel.addDeliveryAddress(apiListener(), viewModel.deliveryAddress)
                            .observe(this,
                                Observer { response ->
                                    run {
                                        if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                                            finish()
                                        } else showToast(
                                            response.message
                                                ?: resources.getString(R.string.msg_something_went_wrong)
                                        )
                                    }

                                })
                    } catch (e: RequiredFieldExceptions) {
                        showToast(e.localizedMessage)
                    }
                else
                    try {
                        viewModel.deliveryAddress.isInputValid(this@AddressActivity)
                        viewModel.updateDeliveryAddress(apiListener(), viewModel.deliveryAddress)
                            .observe(this,
                                Observer { response ->
                                    run {
                                        if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                                            finish()
                                        } else showToast(
                                            response.message
                                                ?: resources.getString(R.string.msg_something_went_wrong)
                                        )
                                    }

                                })
                    } catch (e: RequiredFieldExceptions) {
                        showToast(e.localizedMessage)
                    }

            }
        }
    }
}