package com.io.app.shakomako.ui.deal

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.deal.PendingDealsResponse
import com.io.app.shakomako.api.pojo.invoice.InvoiceData
import com.io.app.shakomako.databinding.LayoutInvoiceDialogBinding
import com.io.app.shakomako.databinding.MyDealFragmentBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.deal.adapter.DoneDealAdapter
import com.io.app.shakomako.ui.deal.adapter.PendingDealAdapter
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.invoice.ChatInvoiceActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant

class MyDealFragment : HomeBaseFragment<MyDealFragmentBinding>(), ViewClickCallback {

    var isDealPending: Boolean = false
    var isDealDone: Boolean = false

    private var pendingDeals: List<PendingDealsResponse> = ArrayList()
    private var doneDeals: List<PendingDealsResponse> = ArrayList()
    private lateinit var pendingDealsAdapter: PendingDealAdapter
    private lateinit var doneAdapter: DoneDealAdapter

    override fun onStart() {
        super.onStart()
        isDealPending = true
        isDealDone = true

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.dealObserver.screenObserver = 0
        isDealPending = false
        isDealDone = false
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun layoutRes(): Int {
        return R.layout.my_deal_fragment
    }

    /**
     *
     */
    private fun init() {
        viewDataBinding.viewHandler = this
        viewDataBinding.viewModel = viewModel
        Log.e("MYDEalFragment", "${viewModel.chatObserver.screenObserver}")

        pendingDealsAdapter =
            PendingDealAdapter(
                getBaseActivity(),
                object : RecyclerClickHandler<View, PendingDealsResponse, Int> {
                    override fun onClick(k: View, l: PendingDealsResponse, m: Int) {
                        when (k.id) {
                            R.id.ll_ici -> {
                                getInvoiceById(l.invoice_id)
                            }
                        }


                    }
                })
        viewDataBinding.rvPendingDeals.adapter = pendingDealsAdapter

        doneAdapter =
            DoneDealAdapter(getBaseActivity(), object : RecyclerClickHandler<Int, Int, Int> {
                override fun onClick(k: Int, l: Int, m: Int) {
                    startActivity(Intent(getBaseActivity(), ChatInvoiceActivity::class.java))
                }
            })
        viewDataBinding.rvDoneDeals.adapter = doneAdapter
    }

    private fun getInvoiceById(id: Int) {
        viewModel.getInvoiceById(apiListener(), id)
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        if (viewModel.chatObserver.screenObserver == 0)
                            showDialog(response.body ?: InvoiceData())
                        else {
                            startActivity(
                                Intent(
                                    getThisActivity(),
                                    ChatInvoiceActivity::class.java
                                ).putExtra(
                                    AppConstant.TYPE,
                                    AppConstant.VIEW_INVOICE_BUSINESS
                                ).putExtra(
                                    AppConstant.PARCEL_DATA,
                                    (response.body ?: InvoiceData())
                                )
                            )
                        }
                    } else showToast(resources.getString(R.string.msg_something_went_wrong))
                }
            })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_pending_deals -> {
                when (viewModel.chatObserver.screenObserver) {
                    0 -> {
                        if (viewModel.dealObserver.screenObserver == 1)
                            viewModel.dealObserver.screenObserver = 0
                        else {
                            if (isDealPending) {
                                viewModel.getPendingDeals(apiListener(), "open").observe(this,
                                    androidx.lifecycle.Observer {
                                        viewModel.dealObserver.screenObserver = 1
                                        isDealPending = !isDealPending
                                        pendingDeals = it.body!!
                                        pendingDealsAdapter.dealsList = pendingDeals

                                    })
                            } else {
                                viewModel.dealObserver.screenObserver = 1
                            }
                            pendingDealsAdapter.dealsList = pendingDeals
                        }
                    }

                    1 -> {
                        if (viewModel.dealObserver.screenObserver == 1)
                            viewModel.dealObserver.screenObserver = 0
                        else {
                            if (isDealPending) {
                                viewModel.getBusinessDeals(apiListener(), "open").observe(this,
                                    androidx.lifecycle.Observer {
                                        viewModel.dealObserver.screenObserver = 1
                                        isDealPending = !isDealPending
                                        pendingDeals = it.body!!
                                        pendingDealsAdapter.dealsList = pendingDeals

                                    })
                            } else {
                                viewModel.dealObserver.screenObserver = 1
                            }
                            pendingDealsAdapter.dealsList = pendingDeals
                        }
                    }

                }
            }

            R.id.rl_done_deals -> {
                when (viewModel.chatObserver.screenObserver) {
                    0 -> {
                        when {
                            viewModel.dealObserver.screenObserver == 2 -> viewModel.dealObserver.screenObserver =
                                0
                            isDealDone -> {
                                viewModel.getPendingDeals(apiListener(), "close").observe(this,
                                    androidx.lifecycle.Observer {
                                        viewModel.dealObserver.screenObserver = 2
                                        isDealDone = !isDealDone
                                        doneDeals = it.body!!
                                        doneAdapter.dealsList = doneDeals

                                    })
                            }
                            else -> {
                                viewModel.dealObserver.screenObserver = 2
                                doneAdapter.dealsList = doneDeals
                            }

                        }
                    }

                    1 -> {
                        when {
                            viewModel.dealObserver.screenObserver == 2 -> viewModel.dealObserver.screenObserver =
                                0
                            isDealDone -> {
                                viewModel.getBusinessDeals(apiListener(), "close").observe(this,
                                    androidx.lifecycle.Observer {
                                        viewModel.dealObserver.screenObserver = 2
                                        isDealDone = !isDealDone
                                        doneDeals = it.body!!
                                        doneAdapter.dealsList = doneDeals

                                    })
                            }
                            else -> {
                                viewModel.dealObserver.screenObserver = 2
                                doneAdapter.dealsList = doneDeals
                            }

                        }
                    }
                }


            }

            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }


    private fun showDialog(it: InvoiceData) {
        val dialog = Dialog(getThisActivity(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        val layoutInvoiceDialogBinding = DataBindingUtil.inflate<LayoutInvoiceDialogBinding>(
            LayoutInflater.from(getThisActivity()), R.layout.layout_invoice_dialog, null, false
        )
        dialog.run {
            setContentView(layoutInvoiceDialogBinding.root)

            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.setDimAmount(0.8f)
            setCancelable(false)
        }

        Glide.with(layoutInvoiceDialogBinding.root).load(it.businessDetails.business_picture)
            .into(layoutInvoiceDialogBinding.itemImage)

        layoutInvoiceDialogBinding.viewHandler = object : ViewClickCallback {
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.iv_clear -> dialog.dismiss()

                    R.id.tv_follow -> viewModel.userFollow(
                        apiListener(),
                        layoutInvoiceDialogBinding.tvFollow,
                        it.business_id
                    )
                }
            }
        }
        layoutInvoiceDialogBinding.data = it
        dialog.show()
    }

}