package com.io.app.shakomako.ui.business

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.business.OtherBusinessProduct
import com.io.app.shakomako.api.pojo.business.OtherBusinessProfileResponse
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.api.pojo.chathistory.CheckChatHistoryResponse
import com.io.app.shakomako.api.pojo.chatroom.ChatRoomData
import com.io.app.shakomako.databinding.FragmentOtherBusinessProfileBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.business.adapter.ProductAdapter
import com.io.app.shakomako.ui.chat.activity.ChatActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.AppConstant.Companion.SHOP_ITEM_DETAILS
import com.io.app.shakomako.utils.session.SessionConstants

class OtherBusinessProfile : HomeBaseFragment<FragmentOtherBusinessProfileBinding>(),
    ViewClickCallback {

    private lateinit var productAdapter: ProductAdapter

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
                        saveRecentActivity()
                    } else showToast(
                        response.message
                            ?: getBaseActivity().resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun saveRecentActivity() {
        viewModel.saveRecentActivity(
            0,
            viewModel.otherBusinessObserver.otherBusinessProfile.businessId,
            AppConstant.BUSINESS
        )
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
                checkChatHistory()
                //openFragment(AppConstant.CHAT_FRAGMENT)
            }
        }
    }

    private fun checkChatHistory() {
        viewModel.checkChatHistory(
            viewModel.userSession.getIntValue(SessionConstants.USER_ID),
            viewModel.otherBusinessObserver.otherBusinessProfile.businessId,
            apiListener()
        ).observe(
            viewLifecycleOwner,
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        if (response.body == null || (response.body
                                ?: CheckChatHistoryResponse()).roomId == 0
                        ) {
                            showToast("No chat has been created yet.")
                        } else {
                            val data = PersonalChatResponse()
                            data.room_id = (response.body ?: CheckChatHistoryResponse()).roomId
                            data.business_id =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessId
                            //data.lastMessage = this.data.product_id.toString()
                            data.business_name =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessName
                            data.business_picture =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessPicture // getting this data from other business profile. Setting value in Other Business Profile

                            startActivity(
                                Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                                    AppConstant.PARCEL_DATA,
                                    data
                                ).putExtra(AppConstant.TYPE, AppConstant.PERSONAL_CHAT)
                                    .putExtra(
                                        AppConstant.EXTRA_DATA,
                                        viewModel.otherBusinessObserver.otherBusinessProduct
                                    )
                            )
                        }
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }
}