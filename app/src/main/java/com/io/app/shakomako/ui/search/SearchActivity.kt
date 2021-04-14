package com.io.app.shakomako.ui.search

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.recentproducts.RecentProducts
import com.io.app.shakomako.api.recentbusiness.RecentBusiness
import com.io.app.shakomako.databinding.ActivitySearchBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.adapter.RecentBusinessAdapter
import com.io.app.shakomako.ui.home.adapter.RecentProductAdapter
import com.io.app.shakomako.ui.search.adapter.SearchQueryAdapter
import com.io.app.shakomako.utils.constants.ApiConstant

class SearchActivity : DataBindingActivity<ActivitySearchBinding>() {
    override fun layoutRes(): Int = R.layout.activity_search

    lateinit var searchViewModel: SearchViewModel


    lateinit var recentBusinessAdapter: RecentBusinessAdapter
    lateinit var recentProductAdapter: RecentProductAdapter
    lateinit var searchQueryAdapter: SearchQueryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = getViewModel(SearchViewModel::class.java)

        init()
        search()
    }

    /**
     * Initial Function call
     */

    private fun init() {
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

        searchQueryAdapter = SearchQueryAdapter()

        bindViewModel<SearchViewModel> {
            adapter = recentBusinessAdapter
            productadapter = recentProductAdapter
            viewModel = searchViewModel
            searchadapter = searchQueryAdapter
        }

        callBusinessApi()

    }

    private fun callBusinessApi() {
        searchViewModel.getRecentBusiness(apiListener()).observe(this, Observer {
            if (it.status == ApiConstant.SUCCESS) {
                if (it.body.isNullOrEmpty()) {
                    searchViewModel.visibleSearchObserver.shopVisible = 1
                } else {
                    searchViewModel.visibleSearchObserver.shopVisible = 0
                }

                recentBusinessAdapter.businessData = it.body!!

                callProductApi()
            } else {
                searchViewModel.visibleSearchObserver.shopVisible = 1
            }
        })
    }


    private fun callProductApi() {
        searchViewModel.getRecentProducts(apiListener()).observe(this, Observer {
            if (it.status == ApiConstant.SUCCESS) {
                if (it.body.isNullOrEmpty()) {
                    searchViewModel.visibleSearchObserver.visible = 1
                } else {
                    searchViewModel.visibleSearchObserver.visible = 0
                }
                recentProductAdapter.productData = it.body!!

            } else {
                searchViewModel.visibleSearchObserver.visible = 1
            }
        })
    }

    fun search() {
        dataBinding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                Log.e("onQueryTextChange", "$newText")
                if (newText.isEmpty()) {
                    Log.e("onQueryTextChange", "text $newText")
                    searchViewModel.visibleSearchObserver.viewVisible = 0
                    return true

                } else {
                    searchViewModel.getSearchByQuery(newText, apiListener())
                        .observe(this@SearchActivity, Observer {
                            if (it.status == ApiConstant.SUCCESS) {
                                Log.e("onQueryTextChange", "response api ")
                                    searchQueryAdapter.addList(it.body!!)
                            }

                        })

                    searchViewModel.visibleSearchObserver.viewVisible = 1
                }


                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                Log.e("onQueryTextSubmit", "$query")
                return false
            }

        })

    }

}