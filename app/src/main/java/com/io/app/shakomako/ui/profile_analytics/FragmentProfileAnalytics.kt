package com.io.app.shakomako.ui.profile_analytics

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentProfileAnalyticsBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.AppConstant

class FragmentProfileAnalytics : HomeBaseFragment<FragmentProfileAnalyticsBinding>(),
    ViewClickCallback {
    override fun layoutRes(): Int = R.layout.fragment_profile_analytics

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_customers -> {
                openFragment(AppConstant.ANALYTICS)
            }

            R.id.ll_verify -> {
                openFragment(AppConstant.VERIFY_BUSINESS_FRAGMENT)
            }
        }
    }

}