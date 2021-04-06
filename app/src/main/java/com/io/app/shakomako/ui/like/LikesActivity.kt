package com.io.app.shakomako.ui.like

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivityLikesBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.like.adapter.LikeAdapterLeft
import com.io.app.shakomako.ui.like.adapter.LikeAdapterRight
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.listener.PaginationListener

class LikesActivity : DataBindingActivity<ActivityLikesBinding>(), ViewClickCallback {

    lateinit var viewModel: LikeViewModel
    lateinit var productAdapter: LikeAdapterLeft
    lateinit var businessAdapter: LikeAdapterRight
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var linearLayoutManager: LinearLayoutManager

    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 0
    private var OFFSET = PAGE_START
    private val LIMIT = 20

    private var isBusinessLoading = false
    private var BUSINESS_OFFSET = PAGE_START
    private val BUSINESS_LIMIT = 20
    private var isBusinessLastPage = false

    override fun layoutRes(): Int {
        return R.layout.activity_likes
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(LikeViewModel::class.java)
        init()
    }

    private fun init() {
        dataBinding.viewHandler = this

        gridLayoutManager = GridLayoutManager(this, 2)
        dataBinding.rvLikesProduct.layoutManager = gridLayoutManager

        linearLayoutManager = LinearLayoutManager(this)
        dataBinding.rvLikesBusiness.layoutManager = linearLayoutManager

        productAdapter = LikeAdapterLeft(this)
        businessAdapter = LikeAdapterRight(this)

        dataBinding.rvLikesProduct.adapter = productAdapter
        dataBinding.rvLikesBusiness.adapter = businessAdapter

        getLikedProducts()
        getLikedBusiness()

        productsListener()
        businessListener()

    }

    private fun productsListener() {
        dataBinding.rvLikesProduct.addOnScrollListener(object :
            PaginationListener(gridLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                OFFSET += 1
                productAdapter.addLoading();
                getLikedProducts()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun businessListener() {
        dataBinding.rvLikesBusiness.addOnScrollListener(object :
            PaginationListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isBusinessLoading = true
                BUSINESS_OFFSET += 1
                businessAdapter.addLoading()
                getLikedBusiness()
            }

            override fun isLastPage(): Boolean {
                return isBusinessLastPage
            }

            override fun isLoading(): Boolean {
                return isBusinessLoading
            }
        })
    }

    private fun getLikedProducts() {
        viewModel.getLikedProducts(apiListener(), LIMIT, OFFSET)
            .observe(this, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        if ((response.body ?: ArrayList()).isEmpty()) {
                            isLastPage = true
                            isLoading = false
                            productAdapter.removeLoading()
                            return@run
                        }
                        productAdapter.addAllData(response.body ?: ArrayList())
                        isLoading = false
                        productAdapter.removeLoading()
                        Log.e("TAG", "DATA SET SUCCESSFUL");
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun getLikedBusiness() {
        viewModel.getLikedBusiness(apiListener(), BUSINESS_LIMIT, BUSINESS_OFFSET).observe(this,
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        if ((response.body ?: ArrayList()).isEmpty()) {
                            isBusinessLastPage = true
                            isBusinessLoading = false
                            businessAdapter.removeLoading()
                            return@run
                        }
                        businessAdapter.addAllData(response.body ?: ArrayList())
                        isBusinessLoading = false
                        businessAdapter.removeLoading()
                        Log.e("TAG", "DATA SET SUCCESSFUL");
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBackPressed()

            R.id.ll_my_deal -> {
            }
        }
    }


}