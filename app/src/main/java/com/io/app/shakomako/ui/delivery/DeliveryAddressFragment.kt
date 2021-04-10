package com.io.app.shakomako.ui.delivery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.databinding.FragmentDeliveryAddressBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.address.AddressActivity
import com.io.app.shakomako.ui.delivery.adapter.DeliveryAddressAdapter
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant

class DeliveryAddressFragment : HomeBaseFragment<FragmentDeliveryAddressBinding>(),
    ViewClickCallback {
    lateinit var adapter: DeliveryAddressAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun layoutRes(): Int {
        return R.layout.fragment_delivery_address
    }

    override fun onResume() {
        super.onResume()
        getDeliveryAddress()
    }

    private fun getDeliveryAddress() {
        viewModel.getDeliveryAddress(apiListener())
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        adapter.list = response.body ?: ArrayList()
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun init() {
        viewDataBinding.viewHandler = this

        adapter = DeliveryAddressAdapter(
            getBaseActivity(),
            object : RecyclerClickHandler<Int, DeliveryAddress, Int> {
                override fun onClick(k: Int, l: DeliveryAddress, m: Int) {
                    if (k == 0) {
                        startActivity(
                            Intent(
                                getBaseActivity(),
                                AddressActivity::class.java
                            ).putExtra(AppConstant.PARCEL_DATA, l)
                                .putExtra(AppConstant.TYPE, AppConstant.UPDATE_ADDRESS)
                        )
                    } else if (k == 1) {
                        viewModel.deleteDeliveryAddress(apiListener(), l.addressId)
                            .observe(viewLifecycleOwner, Observer { response ->
                                run {
                                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                                        getDeliveryAddress()
                                    } else showToast(
                                        response.message
                                            ?: resources.getString(R.string.msg_something_went_wrong)
                                    )
                                }
                            })
                    }
                }
            })
        viewDataBinding.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.ll_delivery_address -> {
                startActivity(
                    Intent(getBaseActivity(), AddressActivity::class.java).putExtra(
                        AppConstant.TYPE,
                        AppConstant.ADD_ADDRESS
                    ).putExtra(AppConstant.PARCEL_DATA, DeliveryAddress())
                )
            }
        }
    }

}