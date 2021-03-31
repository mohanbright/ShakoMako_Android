package com.io.app.shakomako.ui.trending

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.TrendingFragmentBinding
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.trending.adapter.TrendingAdapter
import com.io.app.shakomako.utils.ContextUtils


class TrendingFragment : BaseFragment<TrendingFragmentBinding>() {

    private lateinit var adapter: TrendingAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TrendingAdapter(ContextUtils.imageList())
        val flexBoxLayoutManager = FlexboxLayoutManager(getThisActivity()).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }
        viewDataBinding.recyclerview?.layoutManager = flexBoxLayoutManager

        viewDataBinding.trendingAdapter = adapter


    }

    override fun onResume() {
        super.onResume()
        isHomeActivity(false)
    }

    override fun layoutRes(): Int {
        return R.layout.trending_fragment
    }


}