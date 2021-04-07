package com.io.app.shakomako.ui.home

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.profile.ProfileResponse
import com.io.app.shakomako.databinding.ActivityHomeBinding
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.session.SessionConstants


class HomeActivity : DataBindingActivity<ActivityHomeBinding>() {

    override fun layoutRes(): Int = R.layout.activity_home

    lateinit var homeViewModel: HomeViewModel
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = bindViewModel {

        }
        init()
    }

    private fun init() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_main)

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment?)!!
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        getUserProfile()
    }

    fun callHomeViewModel(): HomeViewModel {
        return homeViewModel
    }

    fun openFragment(type: Int) {
        when (type) {
            AppConstant.CHAT_FRAGMENT -> {
                open(R.id.action_to_chat)
            }
            AppConstant.VERIFY_BUSINESS_FRAGMENT -> {
                open(R.id.action_to_verify_business)
            }
            AppConstant.MY_DEAL -> {
                open(R.id.action_to_my_deal)
            }

            AppConstant.PROFILE_ANALYTICS -> {
                open(R.id.action_to_profile_analytics)
            }

            AppConstant.ANALYTICS -> {
                open(R.id.action_to_analytics)
            }

            AppConstant.VERIFY_PROFILE_FRAGMENT -> {
                open(R.id.action_to_verify_profile)
            }

            AppConstant.DELIVERY_ADDRESS -> {
                open(R.id.action_to_delivery_address)
            }

            AppConstant.PRODUCT_DETAIL -> {
                open(R.id.action_to_product_detail)
            }

            AppConstant.EDIT_PROFILE -> {
                open(R.id.action_to_edit_profile)
            }

            AppConstant.SHOP_ITEM_DETAILS -> {
                open(R.id.action_to_shop_item_detail)
            }

            AppConstant.NAVIGATION_SHOP_DETAIL -> {
                open(R.id.action_to_navigation_shop_detail)
            }
        }
    }

    fun openFragmentWithData(type: Int, bundle: Bundle) {
        when (type) {
            AppConstant.PRODUCT_DETAIL -> {
                openWithData(R.id.action_to_product_detail, bundle)
            }
        }
    }

    private fun openWithData(id: Int, bundle: Bundle) {
        navController.navigate(id, bundle)
    }

    private fun open(id: Int) {
        navController.navigate(id)
    }

    fun isHomeActivity(isVisible: Boolean) {
        if (isVisible) dataBinding.ivAdd.background =
            ContextCompat.getDrawable(getThisActivity(), R.drawable.bg_center_nav_option_blue)
        else dataBinding.ivAdd.background =
            ContextCompat.getDrawable(getThisActivity(), R.drawable.bg_center_nav_option)
    }

    private fun getUserProfile() {
        homeViewModel.getUserProfile(apiListener()).observe(this, Observer { response ->
            run {
                if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                    val data = (response.body ?: ProfileResponse())
                    homeViewModel.userSession.save(
                        SessionConstants.BUSINESS_ID,
                        data.businessId
                    )
                    homeViewModel.userSession.save(
                        SessionConstants.USER_ID,
                        data.userId
                    )

                    homeViewModel.userSession.save(SessionConstants.USER_PROFILE, data.userImage)
                }
            }
        })
    }


}
