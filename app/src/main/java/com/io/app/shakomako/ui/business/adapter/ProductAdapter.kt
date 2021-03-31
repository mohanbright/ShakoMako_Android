package com.io.app.shakomako.ui.business.adapter

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
import com.io.app.shakomako.api.pojo.business.OtherBusinessProduct
import com.io.app.shakomako.databinding.LayoutBusinessProductItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.utils.constants.AppConstant

class ProductAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<Int, OtherBusinessProduct, Int>
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var list: List<OtherBusinessProduct> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ProductViewHolder(var binding: LayoutBusinessProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: OtherBusinessProduct) {
            binding.tvProductPrice.text = data.productAskingPrice + AppConstant.CURRENCY
            loadImage(data.productImages[0])

            binding.root.setOnClickListener { clickHandler.onClick(0, data, adapterPosition) }
        }

        @SuppressLint("CheckResult")
        private fun loadImage(url: String) {
            Glide.with(binding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBarMedium.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBarMedium.visibility = View.GONE
                        return false
                    }
                }).into(binding.ivItem)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: LayoutBusinessProductItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_business_product_item,
            parent,
            false
        )

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(list[position])
    }
}