package com.io.app.shakomako.ui.rating

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentRatingBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.home.HomeBaseFragment


class RatingFragment : HomeBaseFragment<FragmentRatingBinding>(), ViewClickCallback {

    override fun layoutRes(): Int {
        return R.layout.fragment_rating
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {

        viewDataBinding.viewHandler = this
        viewDataBinding.data = viewModel.shopItemDetailObserver.productResponse


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBackPressed()
        }
    }

}