package com.io.app.shakomako.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.home.HomeData
import com.io.app.shakomako.api.pojo.home.HomeItem
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData
import com.io.app.shakomako.databinding.FragmentHomeBinding
import com.io.app.shakomako.helper.callback.RecyclerDataCallBack
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.filter.FilterActivity
import com.io.app.shakomako.ui.home.HomeActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.home.HomeViewModel
import com.io.app.shakomako.ui.home.adapter.HomeAdapter
import com.io.app.shakomako.ui.like.LikesActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant

class HomeFragment : HomeBaseFragment<FragmentHomeBinding>(), ViewClickCallback {
    override fun layoutRes(): Int = R.layout.fragment_home
    private var adapter: HomeAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this


        adapter = HomeAdapter(
            getBaseActivity(),
            object : RecyclerDataCallBack<Int, HomeItem, HomeFashionData, Int> {
                override fun onClick(
                    k: Int,
                    l: HomeItem,
                    m: HomeFashionData,
                    n: Int
                ) {
                    if (k == 0) {
                        viewModel.homeObserver.homeFashionData = m
                        openFragment(AppConstant.PRODUCT_DETAIL)
                    } else if (k == 1) {
                        viewModel.navigationShopObserver.homeItem = l
                        openFragment(AppConstant.NAVIGATION_SHOP_DETAIL)
                    }
                }
            })
        viewDataBinding.rvHome.adapter = adapter

        viewModel.getHomePageData(apiListener()).observe(viewLifecycleOwner, Observer { response ->
            run {
                if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                    addDataToList(response.body ?: HomeData())
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        isHomeActivity(true)
    }

    private fun addDataToList(homeData: HomeData) {
        val list: ArrayList<HomeItem> = ArrayList()
        list.add(
            HomeItem(
                resources.getString(R.string.beauty),
                homeData.beauty as ArrayList<HomeFashionData>
            )
        )
        list.add(
            HomeItem(
                resources.getString(R.string.fashion),
                homeData.fashion as ArrayList<HomeFashionData>
            )
        )
        list.add(
            HomeItem(
                resources.getString(R.string.electronics),
                homeData.electronics as ArrayList<HomeFashionData>
            )
        )
        list.add(
            HomeItem(
                resources.getString(R.string.travel),
                homeData.travel as ArrayList<HomeFashionData>
            )
        )
        list.add(
            HomeItem(
                resources.getString(R.string.hotels),
                homeData.hotel as ArrayList<HomeFashionData>
            )
        )

        adapter?.list = list
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_my_deal -> {
                openFragment(AppConstant.MY_DEAL)
            }

            R.id.ll_likes -> {
                startActivity(Intent(getBaseActivity(), LikesActivity::class.java))
            }
        }
    }
}