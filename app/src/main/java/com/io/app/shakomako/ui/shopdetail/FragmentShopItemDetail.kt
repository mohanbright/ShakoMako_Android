package com.io.app.shakomako.ui.shopdetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData
import com.io.app.shakomako.api.pojo.recentproducts.RecentProducts
import com.io.app.shakomako.api.recentbusiness.RecentBusiness
import com.io.app.shakomako.databinding.FragmentShopItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.filter.FilterActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.home.HomeViewModel
import com.io.app.shakomako.ui.home.adapter.HomeChildAdapter
import com.io.app.shakomako.ui.home.adapter.RecentBusinessAdapter
import com.io.app.shakomako.ui.home.adapter.RecentProductAdapter
import com.io.app.shakomako.utils.constants.ApiConstant

class FragmentShopItemDetail : HomeBaseFragment<FragmentShopItemBinding>(), ViewClickCallback {
    override fun layoutRes(): Int = R.layout.fragment_shop_item

    lateinit var recentBusinessAdapter: RecentBusinessAdapter
    lateinit var recentProductAdapter: RecentProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recentBusinessAdapter =
            RecentBusinessAdapter(
                getThisActivity(),
                object : RecyclerClickHandler<Int, RecentBusiness, Int> {
                    override fun onClick(k: Int, l: RecentBusiness, m: Int) {

                    }
                })

        recentProductAdapter =
            RecentProductAdapter(
                getThisActivity(),
                object : RecyclerClickHandler<Int, RecentProducts, Int> {
                    override fun onClick(k: Int, l: RecentProducts, m: Int) {
//work pending

                    }
                })
        viewModel = bindViewModel {
            adapter = recentBusinessAdapter
            productadapter = recentProductAdapter
            viewHandler = this@FragmentShopItemDetail
        }
        callBusinessApi()
    }


    private fun callBusinessApi() {
        viewModel.getRecentBusiness(apiListener()).observe(viewLifecycleOwner, Observer {
            if (it.status == ApiConstant.SUCCESS) {
                recentBusinessAdapter.businessData = it.body!!
                callProductApi()
            }
        })
    }

    private fun callProductApi() {
        viewModel.getRecentProducts(apiListener()).observe(viewLifecycleOwner, Observer {
            if (it.status == ApiConstant.SUCCESS) {
                recentProductAdapter.productData = it.body!!

            }
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_sort_by -> {


            }
            R.id.rl_filter -> {
                startNewActivity(FilterActivity::class.java)

            }
            R.id.iv_back_arrow -> {
                onBackPressed()
            }
        }
    }
}