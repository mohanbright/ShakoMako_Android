package com.io.app.shakomako.ui.filter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentBrandBinding
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.filter.adapter.LayoutItemBrandAdapter

class BrandFragment : BaseFragment<FragmentBrandBinding>() {

        public fun create() : BrandFragment= BrandFragment()
    

    override fun layoutRes(): Int = R.layout.fragment_brand

    lateinit var layoutItemBrandAdapter: LayoutItemBrandAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        layoutItemBrandAdapter = LayoutItemBrandAdapter(getThisActivity())
        viewDataBinding.apply {
            adapter = layoutItemBrandAdapter
        }


    }


}