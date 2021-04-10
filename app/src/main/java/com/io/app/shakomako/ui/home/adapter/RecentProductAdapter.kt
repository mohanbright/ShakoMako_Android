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
import com.io.app.shakomako.api.pojo.recentproducts.RecentProducts
import com.io.app.shakomako.databinding.LayoutRecentProductsItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class RecentProductAdapter(
    val context: Context,
    var clickHandler: RecyclerClickHandler<Int, RecentProducts, Int>
) :
    RecyclerView.Adapter<RecentProductAdapter.ProductViewHolder>() {

    var productData: List<RecentProducts> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutRecentProductsItemBinding: LayoutRecentProductsItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_recent_products_item,
                parent,
                false
            )
        return ProductViewHolder(layoutRecentProductsItemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productData[position])
    }

    override fun getItemCount(): Int {
        return productData.size
    }

    inner class ProductViewHolder(private var layoutRecentProductsItemBinding: LayoutRecentProductsItemBinding) :
        RecyclerView.ViewHolder(layoutRecentProductsItemBinding.root), ViewClickCallback {

        fun bind(productData: RecentProducts) {
            layoutRecentProductsItemBinding.viewHandler = this
            layoutRecentProductsItemBinding.data = productData
            if (productData.product_images.isNotEmpty()) {
                loadImage(productData.product_images[0])
            }
        }

        @SuppressLint("CheckResult")
        private fun loadImage(url: String) {
            Glide.with(layoutRecentProductsItemBinding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutRecentProductsItemBinding.progressBarMedium.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutRecentProductsItemBinding.progressBarMedium.visibility = View.GONE
                        return false
                    }
                }).into(layoutRecentProductsItemBinding.ivItem)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.rl_product -> {
                    clickHandler.onClick(0, productData[adapterPosition], 0)
                }
            }
        }

    }
}