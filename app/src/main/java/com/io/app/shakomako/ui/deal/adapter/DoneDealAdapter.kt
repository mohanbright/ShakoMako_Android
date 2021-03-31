package com.io.app.shakomako.ui.deal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.LayoutDoneDealItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class DoneDealAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<Int, Int, Int>
) :
    RecyclerView.Adapter<DoneDealAdapter.DoneDealViewHolder>() {


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
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class DoneDealViewHolder(var layoutDoneDealItemBinding: LayoutDoneDealItemBinding) :
        RecyclerView.ViewHolder(layoutDoneDealItemBinding.root), ViewClickCallback {

        fun bind() {
            layoutDoneDealItemBinding.viewHandler = this
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