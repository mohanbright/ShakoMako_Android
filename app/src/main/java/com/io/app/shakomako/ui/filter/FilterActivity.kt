package com.io.app.shakomako.ui.filter

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivityFilterBinding
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.filter.adapter.PagerAdapter
import com.io.app.shakomako.ui.filter.fragment.BrandFragment
import com.io.app.shakomako.ui.filter.fragment.CategoryFragment

class FilterActivity : DataBindingActivity<ActivityFilterBinding>() {
    override fun layoutRes(): Int = R.layout.activity_filter

    lateinit var pagerAdapter: PagerAdapter
    lateinit var brandFragment: BrandFragment
    lateinit var categoryFragment: CategoryFragment




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pagerAdapter = PagerAdapter(supportFragmentManager)
        initFilter()

    }

    /**
     * initial
     */
    private fun initFilter() {
        initFragment()

        dataBinding.adapter = pagerAdapter
        dataBinding.tab.setupWithViewPager(dataBinding.viewpager)
        addFragment()
    }

    private fun initFragment() {
        brandFragment = BrandFragment()
        categoryFragment = CategoryFragment()
    }


    private fun addFragment() {
        pagerAdapter.addTitle(resources.getString(R.string.brand))
        pagerAdapter.addTitle(resources.getString(R.string.category))
        pagerAdapter.addFragment(brandFragment)
        pagerAdapter.addFragment(categoryFragment)
//        pagerAdapter.addFragment(categoryFragment)
    }
}