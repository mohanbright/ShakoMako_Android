package com.io.app.shakomako.ui.deal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.deal.PendingDealsResponse
import com.io.app.shakomako.databinding.LayoutDoneDealItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class DoneDealAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<View, PendingDealsResponse, Int>
) :
    RecyclerView.Adapter<DoneDealAdapter.DoneDealViewHolder>() {

    var dealsList: List<PendingDealsResponse> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneDealViewHolder {
        val layoutDoneDealItemBinding: LayoutDoneDealItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_done_deal_item,
                parent,
                false
            )

        return DoneDealViewHolder(layoutDoneDealItemBinding)
    }

    override fun onBindViewHolder(holder: DoneDealViewHolder, position: Int) {
        holder.bind(dealsList[position])
    }

    override fun getItemCount(): Int {
        return dealsList.size
    }

    inner class DoneDealViewHolder(var layoutDoneDealItemBinding: LayoutDoneDealItemBinding) :
        RecyclerView.ViewHolder(layoutDoneDealItemBinding.root), ViewClickCallback {

        fun bind(data: PendingDealsResponse) {
            layoutDoneDealItemBinding.viewHandler = this
            layoutDoneDealItemBinding.dealsData = data
            layoutDoneDealItemBinding.executePendingBindings()
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ll_ici -> {
                    clickHandler.onClick(v, dealsList[adapterPosition], adapterPosition)
                }
            }
        }

    }
}