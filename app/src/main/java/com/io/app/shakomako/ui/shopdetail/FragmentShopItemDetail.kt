package com.io.app.shakomako.ui.shopdetail

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData
import com.io.app.shakomako.databinding.FragmentShopItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.filter.FilterActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.home.HomeViewModel
import com.io.app.shakomako.ui.home.adapter.HomeChildAdapter

class FragmentShopItemDetail : HomeBaseFragment<FragmentShopItemBinding>(), ViewClickCallback {
    override fun layoutRes(): Int = R.layout.fragment_shop_item

    lateinit var homeChildAdapter: HomeChildAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeChildAdapter =
            HomeChildAdapter(
                getThisActivity(),
                object : RecyclerClickHandler<Int, HomeFashionData, Int> {
                    override fun onClick(k: Int, l: HomeFashionData, m: Int) {

                    }
                })
        homeChildAdapter.homeFashionData = viewModel.navigationShopObserver.homeItem.list

        viewDataBinding.adapter = homeChildAdapter

        viewModel = bindViewModel {
            adapter = homeChildAdapter
            viewHandler = this@FragmentShopItemDetail
        }




    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_sort_by -> {


            }
            R.id.rl_filter -> {
                startNewActivity(FilterActivity::class.java)

            }
        }
    }
}