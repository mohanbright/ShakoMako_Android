package com.io.app.shakomako.ui.deal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.LayoutPendingDealItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class PendingDealAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<Int, Int, Int>
) :
    RecyclerView.Adapter<PendingDealAdapter.PendingDealViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingDealViewHolder {
        val layoutPendingDealItemBinding: LayoutPendingDealItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_pending_deal_item, parent, false
        )

        return PendingDealViewHolder(layoutPendingDealItemBinding)
    }

    override fun onBindViewHolder(holder: PendingDealViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class PendingDealViewHolder(var layoutPendingDealItemBinding: LayoutPendingDealItemBinding) :
        RecyclerView.ViewHolder(layoutPendingDealItemBinding.root), ViewClickCallback {

        fun bind() {
            layoutPendingDealItemBinding.viewHandler = this
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ll_ici -> {
                    clickHandler.onClick(0, 0, 0)
                }
            }
        }

    }
}