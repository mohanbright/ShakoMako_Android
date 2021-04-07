package com.io.app.shakomako.ui.shopitemdetails

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.api.pojo.chatroom.ChatRoomData
import com.io.app.shakomako.api.pojo.product.ProductResponse
import com.io.app.shakomako.databinding.FragmentShopItemDetailBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.chat.activity.ChatActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.like.LikesActivity
import com.io.app.shakomako.ui.shopitemdetails.adapter.SlidingImageAdapter
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.session.SessionConstants

class ShopItemDetailFragment : HomeBaseFragment<FragmentShopItemDetailBinding>(),
    ViewClickCallback {

    var data: ProductResponse = ProductResponse()

    override fun layoutRes(): Int {
        return R.layout.fragment_shop_item_detail
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this@ShopItemDetailFragment
        viewDataBinding.viewModel = viewModel
        getData()
    }

    private fun getData() {
        if (viewModel.shopItemObserver.type == 0) getProductDetail(viewModel.otherBusinessObserver.otherBusinessProduct.productId) else getProductDetail(
            viewModel.shopItemObserver.businessProducts.productId
        )
    }

    private fun getProductDetail(id: Int) {
        viewModel.getProductDetail(apiListener(), id)
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        this.data = response.body ?: ProductResponse()
                        setData()

                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun setData() {
        viewDataBinding.viewPager.adapter = SlidingImageAdapter(
            getThisActivity(),
            data.product_images
        )

        val tabLayoutMediator = TabLayoutMediator(
            viewDataBinding.tlShop,
            viewDataBinding.viewPager, true
        ) { _, _ -> }
        tabLayoutMediator.attach()

        viewDataBinding.tvCurrency.text =
            data.product_asking_price
        viewDataBinding.tvDescription.text =
            data.product_description
        viewDataBinding.isLiked =
            data.selfLiked == "yes"

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_my_deal -> {
                openFragment(AppConstant.MY_DEAL)
            }

            R.id.iv_like, R.id.iv_dislike -> {
                likeUnlikeProduct()
            }

            R.id.tv_related -> {
                viewModel.shopItemDetailObserver.screenObserver = 1
            }
            R.id.tv_overview -> {
                viewModel.shopItemDetailObserver.screenObserver = 0
            }

            R.id.ll_chat -> createChat()

            R.id.ll_likes -> startNewActivity(LikesActivity::class.java)

        }
    }

    private fun createChat() {
        if (data.business_id == viewModel.userSession.getIntValue(SessionConstants.BUSINESS_ID)) {
            viewModel.chatObserver.screenObserver = 1
            openFragment(AppConstant.CHAT_FRAGMENT)
        } else {
            viewModel.createChatRoom(
                apiListener(),
                viewModel.userSession.getIntValue(SessionConstants.USER_ID),
                this.data.business_id
            ).observe(viewLifecycleOwner, Observer { response ->
                run {
                    when {
                        response.status?.equals(ApiConstant.SUCCESS) == true -> {
                            val data = PersonalChatResponse()
                            data.room_id = (response.body ?: ChatRoomData()).roomId
                            data.business_id = this.data.business_id
                            data.lastMessage = this.data.product_id.toString()
                            data.business_name =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessName
                            data.business_picture =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessPicture // getting this data from other business profile. Setting value in Other Business Profile

                            startActivity(
                                Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                                    AppConstant.PARCEL_DATA,
                                    data
                                ).putExtra(AppConstant.TYPE, AppConstant.CREATE_CHAT)
                                    .putExtra(AppConstant.EXTRA_DATA, this.data)
                            )
                        }
                        response.status?.equals(201) == true -> {
                            val data = PersonalChatResponse()
                            data.room_id = (response.body ?: ChatRoomData()).roomId
                            data.business_name =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessName
                            data.business_picture =
                                viewModel.otherBusinessObserver.otherBusinessProfile.businessPicture // getting this data from other business profile. Setting value in Other Business Profile
                            startActivity(
                                Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                                    AppConstant.PARCEL_DATA,
                                    data
                                ).putExtra(AppConstant.TYPE, AppConstant.PERSONAL_CHAT)
                                    .putExtra(AppConstant.EXTRA_DATA, this.data)
                            )
                        }
                        else -> showToast(
                            response.message
                                ?: resources.getString(R.string.msg_something_went_wrong)
                        )
                    }
                }
            })
        }
    }

    private fun likeUnlikeProduct() {
        viewModel.likeUnlikeProduct(apiListener(), data.product_id).observe(viewLifecycleOwner,
            Observer { response ->
                run {
                    when {
                        response.status?.equals(ApiConstant.SUCCESS) == true -> {
                            viewDataBinding.isLiked = true
                        }
                        response.status?.equals(201) == true -> {
                            viewDataBinding.isLiked = false
                        }
                    }
                    showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }


}