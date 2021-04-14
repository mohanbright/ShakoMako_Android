package com.io.app.shakomako.ui.insight


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentInsightAnalyticsBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.ApiConstant


class FragmentInsightAnalytics : HomeBaseFragment<FragmentInsightAnalyticsBinding>(),ViewClickCallback {
    override fun layoutRes(): Int = R.layout.fragment_insight_analytics


    private fun init() {
        viewDataBinding.viewHandler=this@FragmentInsightAnalytics
        viewModel.getInsightData(apiListener()).observe(viewLifecycleOwner, Observer {
            if (it.status == ApiConstant.SUCCESS) {
                viewDataBinding.insightData = it.body
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.iv_back->{
                onBackPressed()
            }
        }
    }


}