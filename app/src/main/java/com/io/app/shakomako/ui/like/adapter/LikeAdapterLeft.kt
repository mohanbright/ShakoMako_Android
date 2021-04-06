package com.io.app.shakomako.ui.like.adapter

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
import com.io.app.shakomako.api.pojo.like.LikedProductData
import com.io.app.shakomako.databinding.LayoutBusinessProductItemBinding
import com.io.app.shakomako.databinding.LayoutItemLoadingBinding

class LikeAdapterLeft(var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var viewHolder: RecyclerView.ViewHolder

    var list: ArrayList<LikedProductData> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_NORMAL = 1
    }

    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        if (viewType == VIEW_TYPE_NORMAL) {
            viewHolder = getViewHolder(parent, layoutInflater)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val binding: LayoutItemLoadingBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.layout_item_loading, parent, false)
            viewHolder = ProgressHolder(binding)
        }

        return viewHolder
    }

    private fun getViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val binding: LayoutBusinessProductItemBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_business_product_item,
            parent,
            false
        )
        viewHolder = LikeViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_NORMAL -> {
                val viewHolder: LikeViewHolder = holder as LikeViewHolder
                viewHolder.bind(list)
            }
            VIEW_TYPE_LOADING -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == list.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    private fun add(data: LikedProductData) {
        Log.e("TAGG", "${data}")
        list.add(data)
        notifyItemInserted(itemCount)
    }

    fun addAllData(list: List<LikedProductData>) {
        for (data in list) {
            add(data)
        }
    }

    fun addLoading() {
        isLoaderVisible = true
        notifyDataSetChanged()
    }

    fun removeLoading() {
        isLoaderVisible = false
        notifyDataSetChanged()
//        val position: Int = list.size - 1
//        try {
//            list.removeAt(position)
//        } catch (e: ArrayIndexOutOfBoundsException) {
//
//        }
//        notifyItemRemoved(position)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }


    inner class LikeViewHolder(var binding: LayoutBusinessProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(list: List<LikedProductData>) {
            binding.tvProductPrice.text = list[adapterPosition].product_asking_price + " IQD"
            try {

                loadImage(list[adapterPosition].product_images[0])
            } catch (e: Exception) {

            }
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
                }).placeholder(R.drawable.placeholder).into(binding.ivItem)
        }
    }

    inner class ProgressHolder(private var loadingBinding: LayoutItemLoadingBinding) :
        RecyclerView.ViewHolder(loadingBinding.root) {


    }


}