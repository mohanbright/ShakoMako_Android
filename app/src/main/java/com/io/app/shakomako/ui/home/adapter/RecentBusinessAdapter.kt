package com.io.app.shakomako.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.io.app.shakomako.R
import com.io.app.shakomako.api.recentbusiness.RecentBusiness
import com.io.app.shakomako.databinding.LayoutRecentBusinessItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class RecentBusinessAdapter(
    val context: Context,
    var clickHandler: RecyclerClickHandler<Int, RecentBusiness, Int>
) :
    RecyclerView.Adapter<RecentBusinessAdapter.BusinessViewHolder>() {

    var businessData: List<RecentBusiness> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val layoutRecentBusinessItemBinding: LayoutRecentBusinessItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_recent_business_item,
                parent,
                false
            )
        return BusinessViewHolder(layoutRecentBusinessItemBinding)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        holder.bind(businessData[position])
    }

    override fun getItemCount(): Int {
        return businessData.size
    }

    inner class BusinessViewHolder(private var layoutRecentBusinessItemBinding: LayoutRecentBusinessItemBinding) :
        RecyclerView.ViewHolder(layoutRecentBusinessItemBinding.root), ViewClickCallback {

        fun bind(businessData: RecentBusiness) {
            layoutRecentBusinessItemBinding.viewHandler = this
            layoutRecentBusinessItemBinding.data = businessData

            loadImage(businessData.business_picture)
        }

        @SuppressLint("CheckResult")
        private fun loadImage(url: String) {
            Glide.with(layoutRecentBusinessItemBinding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutRecentBusinessItemBinding.progressBarMedium?.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutRecentBusinessItemBinding.progressBarMedium.visibility = View.GONE
                        return false
                    }
                }).into(layoutRecentBusinessItemBinding.itemImage)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.rl_product -> {
                    clickHandler.onClick(0, businessData[adapterPosition], 0)
                }
            }
        }

    }
}