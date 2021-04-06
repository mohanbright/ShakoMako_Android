package com.io.app.shakomako.ui.deal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.deal.PendingDealsResponse
import com.io.app.shakomako.databinding.LayoutPendingDealItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class PendingDealAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<View, PendingDealsResponse, Int>
) :
    RecyclerView.Adapter<PendingDealAdapter.PendingDealViewHolder>() {

    var dealsList: List<PendingDealsResponse> = ArrayList()
        set(value) {
            Log.e("PendingDealAdapter", "$value")
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingDealViewHolder {
        val layoutPendingDealItemBinding: LayoutPendingDealItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_pending_deal_item, parent, false
        )

        return PendingDealViewHolder(layoutPendingDealItemBinding)
    }

    override fun onBindViewHolder(holder: PendingDealViewHolder, position: Int) {
        holder.bind(dealsList[position])
    }

    override fun getItemCount(): Int {
        return dealsList.size
    }

    inner class PendingDealViewHolder(var layoutPendingDealItemBinding: LayoutPendingDealItemBinding) :
        RecyclerView.ViewHolder(layoutPendingDealItemBinding.root), ViewClickCallback {

        fun bind(data: PendingDealsResponse) {
            layoutPendingDealItemBinding.viewHandler = this
            layoutPendingDealItemBinding.dealsData = data
            layoutPendingDealItemBinding.executePendingBindings()

            loadImage(data.product_images)

        }

        @SuppressLint("CheckResult")
        private fun loadImage(url: String) {
            Glide.with(layoutPendingDealItemBinding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutPendingDealItemBinding.progressBarMedium.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutPendingDealItemBinding.progressBarMedium.visibility = View.GONE
                        return false
                    }
                }).placeholder(R.drawable.placeholder)
                .into(layoutPendingDealItemBinding.ivProduct)
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