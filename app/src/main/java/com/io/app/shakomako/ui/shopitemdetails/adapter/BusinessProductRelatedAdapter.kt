package com.io.app.shakomako.ui.shopitemdetails.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.product.ProductRelatedResponse
import com.io.app.shakomako.databinding.LayoutBusinessProductItemBinding
import com.io.app.shakomako.databinding.LayoutItemLoadingBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler

class BusinessProductRelatedAdapter(
    var clickHandler: RecyclerClickHandler<Int, ProductRelatedResponse, Int>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var viewHolder: RecyclerView.ViewHolder

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_NORMAL = 1
    }

    private var isLoaderVisible = false

    var relatedResponseList: ArrayList<ProductRelatedResponse> = ArrayList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        if (viewType == VIEW_TYPE_NORMAL) {
            viewHolder = getViewHolder(parent, layoutInflater)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val layoutItemLoadingBinding: LayoutItemLoadingBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.layout_item_loading, parent, false)
            viewHolder = ProgressViewHolder(layoutItemLoadingBinding)
        }

        return viewHolder

    }

    private fun getViewHolder(
        parent: ViewGroup,
        layoutInflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val layoutBusinessProductItemBinding =
            DataBindingUtil.inflate<LayoutBusinessProductItemBinding>(
                layoutInflater,
                R.layout.layout_business_product_item,
                parent,
                false
            )
        return ProductViewHolder(layoutBusinessProductItemBinding)
    }

    override fun getItemCount(): Int = relatedResponseList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_NORMAL -> {
                val productViewHolder = holder as ProductViewHolder
                productViewHolder.bind(relatedResponseList)
            }
            VIEW_TYPE_LOADING -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) if (position == relatedResponseList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL else VIEW_TYPE_NORMAL
    }

    private fun add(data: ProductRelatedResponse) {
        relatedResponseList.add(data)
        notifyItemInserted(itemCount)
    }

    fun setList(relatedResponseList: ArrayList<ProductRelatedResponse>) {
        for (data in relatedResponseList) {
            add(data)
        }
    }

    fun addLoader() {
        isLoaderVisible = true
        notifyDataSetChanged()
    }

    fun removerLoader() {
        isLoaderVisible = false
        notifyDataSetChanged()
    }

    fun clearList() {
        relatedResponseList.clear()
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(var binding: LayoutBusinessProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(relatedResponseList: List<ProductRelatedResponse>) {
            val productRelatedResponse = relatedResponseList[adapterPosition]
            binding.apply {
                tvProductPrice.text = productRelatedResponse.product_asking_price
                if (!productRelatedResponse.product_images.isNullOrEmpty()) {
                    Glide.with(root.context).load(productRelatedResponse.product_images[0])
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Glide.with(root.context).load(R.drawable.placeholder)
                                    .into(ivItem)
                                progressBarMedium.isVisible = false
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressBarMedium.isVisible = false
                                return false
                            }

                        })
                        .into(ivItem)
                } else {
                    Glide.with(root.context).load(R.drawable.placeholder)
                        .into(ivItem)
                }

                root.setOnClickListener {
                    clickHandler.onClick(
                        productRelatedResponse.business_id,
                        productRelatedResponse,
                        adapterPosition
                    )
                }

            }

        }
    }

    inner class ProgressViewHolder(layoutItemLoadingBinding: LayoutItemLoadingBinding) :
        RecyclerView.ViewHolder(layoutItemLoadingBinding.root) {
    }

}