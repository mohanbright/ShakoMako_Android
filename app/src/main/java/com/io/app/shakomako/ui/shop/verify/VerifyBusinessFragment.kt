package com.io.app.shakomako.ui.shop.verify

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.VerifyBusinessFragmentBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.home.HomeBaseFragment

class VerifyBusinessFragment : HomeBaseFragment<VerifyBusinessFragmentBinding>(), ViewClickCallback {

    override fun layoutRes(): Int {
        return R.layout.verify_business_fragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }


}