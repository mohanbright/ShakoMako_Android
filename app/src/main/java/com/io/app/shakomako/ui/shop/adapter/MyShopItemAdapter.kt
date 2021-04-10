package com.io.app.shakomako.ui.shop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
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
import com.io.app.shakomako.api.pojo.shop.BusinessProducts
import com.io.app.shakomako.databinding.LayoutMyShopItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class MyShopItemAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<View, BusinessProducts, Int>
) :
    RecyclerView.Adapter<MyShopItemAdapter.MyShopViewHolder>() {

    var list: List<BusinessProducts> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyShopViewHolder {
        val layoutMyShopItemBinding: LayoutMyShopItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_my_shop_item, parent, false
        )

        return MyShopViewHolder(layoutMyShopItemBinding)
    }

    override fun onBindViewHolder(holder: MyShopViewHolder, position: Int) {
        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyShopViewHolder(private val layoutMyShopItemBinding: LayoutMyShopItemBinding) :
        RecyclerView.ViewHolder(layoutMyShopItemBinding.root), ViewClickCallback {


        fun bind(product: BusinessProducts) {
            layoutMyShopItemBinding.data = product
            layoutMyShopItemBinding.viewHandler = this
            loadImage(product.productImages[0])
        }

        @SuppressLint("CheckResult")
        private fun loadImage(url: String) {
            Glide.with(layoutMyShopItemBinding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutMyShopItemBinding.progressBarMedium?.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutMyShopItemBinding.progressBarMedium?.visibility = View.GONE
                        return false
                    }
                }).into(layoutMyShopItemBinding.ivItem)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.iv_edit, R.id.iv_item -> {
                    clickHandler.onClick(v, list[adapterPosition], adapterPosition)
                }
            }
        }
    }
}