package com.io.app.shakomako.ui.business

import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.business.OtherBusinessProduct
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfileResponse
import com.io.app.shakomako.databinding.FragmentOtherBusinessProfileBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.business.adapter.ProductAdapter
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.AppConstant.Companion.SHOP_ITEM_DETAILS

class OtherBusinessProfile : HomeBaseFragment<FragmentOtherBusinessProfileBinding>(),
    ViewClickCallback {

    private lateinit var productAdapter: ProductAdapter
    //lateinit var keyboard: Keyboard

    override fun layoutRes(): Int {
        return R.layout.fragment_other_business_profile
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this


        // val layoutManager = GridLayoutManager(getBaseActivity(), 3)
        productAdapter = ProductAdapter(
            getBaseActivity(),
            object : RecyclerClickHandler<Int, OtherBusinessProduct, Int> {
                override fun onClick(k: Int, l: OtherBusinessProduct, m: Int) {
                    viewModel.otherBusinessObserver.otherBusinessProduct = l
                    viewModel.shopItemObserver.type = 0
                    openFragment(SHOP_ITEM_DETAILS)
                }
            })
        viewDataBinding.rvBusinessProduct.adapter = productAdapter

        getBusinessProfile()

    }

    private fun getBusinessProfile() {
        viewModel.getOtherBusinessProfile(
            viewModel.homeObserver.homeFashionData.businessId,
            apiListener()
        ).observe(viewLifecycleOwner,
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        viewDataBinding.data = response.body?.businessProfile
                        viewModel.otherBusinessObserver.otherBusinessProfile =
                            (response.body ?: OtherBusinessProfileResponse()).businessProfile
                        setData(response.body ?: OtherBusinessProfileResponse())
                    } else showToast(
                        response.message
                            ?: getBaseActivity().resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun setData(data: OtherBusinessProfileResponse) {
        Glide.with(viewDataBinding.root).load(data.businessProfile.businessPicture)
            .into(viewDataBinding.ivBusiness)
        viewDataBinding.tvEmpty.visibility =
            if (data.businessProducts.isEmpty()) View.VISIBLE else View.GONE
        productAdapter.list = data.businessProducts
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.tv_chat -> {
                openFragment(AppConstant.CHAT_FRAGMENT)
            }
        }
    }
}