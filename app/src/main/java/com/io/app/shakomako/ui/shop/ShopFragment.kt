package com.io.app.shakomako.ui.shop

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.io.app.shakomako.R
import com.io.app.shakomako.api.exception.RequiredFieldExceptions
import com.io.app.shakomako.api.pojo.shop.BusinessDetail
import com.io.app.shakomako.api.pojo.shop.BusinessProducts
import com.io.app.shakomako.api.pojo.shop.BusinessProfile
import com.io.app.shakomako.databinding.ShopFragmentBinding
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.product.AddProductActivity
import com.io.app.shakomako.ui.shop.adapter.MyShopItemAdapter
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.picker.MyOptionsPickerView

class ShopFragment : HomeBaseFragment<ShopFragmentBinding>(), ViewClickCallback {

    lateinit var adapter: MyShopItemAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    override fun onResume() {
        super.onResume()
        isHomeActivity(false)
        getBusinessProfile()
    }

    override fun layoutRes(): Int {
        return R.layout.shop_fragment
    }

    private fun init() {
        viewDataBinding.viewHandler = this
        viewDataBinding.viewModel = viewModel
        setList()

        adapter = MyShopItemAdapter(
            getBaseActivity(),
            object : RecyclerClickHandler<View, BusinessProducts, Int> {
                override fun onClick(k: View, l: BusinessProducts, m: Int) {
                    when (k.id) {
                        R.id.iv_edit -> {
                            Log.e("TAG", "edit")
                            startActivity(
                                Intent(
                                    getBaseActivity(),
                                    AddProductActivity::class.java
                                ).putExtra(AppConstant.PARCEL_DATA, l)
                                    .putExtra(AppConstant.TYPE, AppConstant.EDIT_PRODUCT).putExtra(
                                        AppConstant.PRODUCT_ID,
                                        l.productId
                                    )
                            )
                        }

                        R.id.iv_item -> {
                            viewModel.shopItemObserver.type = 1
                            viewModel.shopItemObserver.businessProducts = l
                            openFragment(AppConstant.SHOP_ITEM_DETAILS)
                        }
                    }
                }
            })
        val layoutManager = GridLayoutManager(getBaseActivity(), 3)
        viewDataBinding.includeMyShop?.rvMyShop?.layoutManager = layoutManager
        viewDataBinding.includeMyShop?.rvMyShop?.adapter = adapter


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_beauty -> {
                hideKeyboard()
                openHouseNumberPicker(getBaseActivity())
            }

            R.id.tv_add_product_ -> {
                addProduct()
            }
            R.id.tv_add_product -> {
                addProduct()
            }

            R.id.tv_chat -> {
                openFragment(AppConstant.CHAT_FRAGMENT)
            }

            R.id.ll_verify -> {
                openFragment(AppConstant.VERIFY_BUSINESS_FRAGMENT)
            }

            R.id.ll_my_deal -> {
                openFragment(AppConstant.MY_DEAL)
            }

            R.id.tv_analytics -> {
                openFragment(AppConstant.PROFILE_ANALYTICS)
            }

            R.id.tv_upload_bio_picture -> {
                openSingleImagePicker(object : DataItemCallBack<Uri, Int> {
                    override fun onItemData(t: Uri?, r: Int?) {
                        try {
                            viewModel.upload(apiListener(), t?.path.toString())
                                .observe(viewLifecycleOwner, Observer { response ->
                                    run {
                                        if (response.status?.equals(ApiConstant.SUCCESS) == true)
                                            viewModel.shopObserver.businessDetail.businessPicture =
                                                response.body?.image.toString()
                                        else showToast(
                                            response.message
                                                ?: getBaseActivity().resources.getString(R.string.msg_something_went_wrong)
                                        )
                                    }
                                })
                        } catch (e: Exception) {
                            Log.e("TAG", e.localizedMessage)
                        }
                    }
                })
            }

            R.id.tv_submit -> {
                try {
                    viewModel.shopObserver.businessDetail.isInputValid()
                    updateBusinessDetails()
                } catch (e: RequiredFieldExceptions) {
                    showToast(e.localizedMessage)
                }
            }

            R.id.iv_edit -> {
                viewModel.shopObserver.screenObserver = 0
            }
        }
    }

    private fun updateBusinessDetails() {
        viewModel.updateBusiness(viewModel.shopObserver.businessDetail, apiListener())
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        viewModel.shopObserver.businessDetail = response.body ?: BusinessDetail()
                        viewModel.shopObserver.screenObserver = 1
                        getBusinessProfile()
                    }
                }
            })
    }

    private fun addProduct() {
        startActivity(
            Intent(
                getBaseActivity(),
                AddProductActivity::class.java
            ).putExtra(
                AppConstant.BUSINESS_ID,
                viewModel.shopObserver.businessProfile.businessProfileDetails.businessId
            )
        )
    }


    private fun getBusinessProfile() {
        viewModel.getBusinessProfile(apiListener())
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    when (response.status) {
                        ApiConstant.NOT_FOUND -> {
                            viewModel.shopObserver.screenObserver = 0
                        }

                        ApiConstant.SUCCESS -> {
                            viewModel.shopObserver.screenObserver = 1
                            adapter.list = response.body?.businessProducts ?: ArrayList()
                            setDataForBusinessDetail(response.body ?: BusinessProfile())
                            viewDataBinding.data = response.body ?: BusinessProfile()
                        }
                    }
                }
            })
    }

    private fun setDataForBusinessDetail(body: BusinessProfile) {
        viewModel.shopObserver.businessDetail.businessName =
            body.businessProfileDetails.businessName
        viewModel.shopObserver.businessDetail.businessCategory =
            body.businessProfileDetails.businessCategory
        viewModel.shopObserver.businessDetail.businessBio = body.businessProfileDetails.businessBio
        viewModel.shopObserver.businessDetail.businessLocation =
            body.businessProfileDetails.businessLocation
        viewModel.shopObserver.businessDetail.businessPicture =
            body.businessProfileDetails.businessPicture

        loadImage(body.businessProfileDetails.businessPicture)
        viewModel.shopObserver.businessProfile = body

    }

    @SuppressLint("CheckResult")
    private fun loadImage(url: String) {
        Glide.with(viewDataBinding.root).load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewDataBinding.includeMyShop?.progressBarMedium?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewDataBinding.includeMyShop?.progressBarMedium?.visibility = View.GONE
                    return false
                }
            }).into(viewDataBinding.includeMyShop?.businessProfileImage!!)
    }

    private fun openHouseNumberPicker(context: Context) {
        val singlePicker = MyOptionsPickerView<String>(context)
        singlePicker.setPicker(list)
        singlePicker.setSubmitButtonText(context.resources.getString(R.string.done))
        singlePicker.setTitle("")
        singlePicker.setCyclic(false)

        singlePicker.setOnCancelClickListener {
            viewDataBinding.tvBeauty.text = resources.getString(R.string.select_business_category)
        }

        singlePicker.setOnoptionsSelectListener { options1, _, _ ->
            try {
                //viewDataBinding.tvBeauty.text = list[options1]
                viewModel.shopObserver.businessDetail.businessCategory = list[options1]
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                singlePicker.dismiss()
            }
        }


        singlePicker.show()
    }


    private val list: ArrayList<String> = ArrayList()

    private fun setList() {
        list.clear()
        list.add((resources.getString(R.string.beauty)))
        list.add((resources.getString(R.string.fashion)))
        list.add((resources.getString(R.string.electronics)))
        list.add((resources.getString(R.string.travel)))
        list.add((resources.getString(R.string.hotels)))
    }

}