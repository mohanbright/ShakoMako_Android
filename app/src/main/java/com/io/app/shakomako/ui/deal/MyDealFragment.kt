package com.io.app.shakomako.ui.deal

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.MyDealFragmentBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.deal.adapter.DoneDealAdapter
import com.io.app.shakomako.ui.deal.adapter.PendingDealAdapter
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.invoice.ChatInvoiceActivity

class MyDealFragment : HomeBaseFragment<MyDealFragmentBinding>(), ViewClickCallback {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun layoutRes(): Int {
        return R.layout.my_deal_fragment
    }


    private fun init() {
        viewDataBinding.viewHandler = this
        viewDataBinding.viewModel = viewModel

        val adapter =
            PendingDealAdapter(getBaseActivity(), object : RecyclerClickHandler<Int, Int, Int> {
                override fun onClick(k: Int, l: Int, m: Int) {
                    startActivity(Intent(getBaseActivity(), ChatInvoiceActivity::class.java))
                }
            })
        viewDataBinding.rvPendingDeals.adapter = adapter

        val doneAdapter =
            DoneDealAdapter(getBaseActivity(), object : RecyclerClickHandler<Int, Int, Int> {
                override fun onClick(k: Int, l: Int, m: Int) {
                    startActivity(Intent(getBaseActivity(), ChatInvoiceActivity::class.java))
                }
            })
        viewDataBinding.rvDoneDeals.adapter = doneAdapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_pending_deals -> {
                if (viewModel.dealObserver.screenObserver == 1)
                    viewModel.dealObserver.screenObserver = 0
                else viewModel.dealObserver.screenObserver = 1
            }

            R.id.rl_done_deals -> {
                if (viewModel.dealObserver.screenObserver == 2)
                    viewModel.dealObserver.screenObserver = 0
                else
                    viewModel.dealObserver.screenObserver = 2
            }

            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

}