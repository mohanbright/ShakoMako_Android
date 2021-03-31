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
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData
import com.io.app.shakomako.databinding.LayoutHomeChildItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class HomeChildAdapter(
    val context: Context,
    var clickHandler: RecyclerClickHandler<Int, HomeFashionData, Int>
) :
    RecyclerView.Adapter<HomeChildAdapter.HomeChildViewHolder>() {

    var homeFashionData: ArrayList<HomeFashionData> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChildViewHolder {
        val layoutHomeChildItemBinding: LayoutHomeChildItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_home_child_item,
                parent,
                false
            )
        return HomeChildViewHolder(layoutHomeChildItemBinding)
    }

    override fun onBindViewHolder(holder: HomeChildViewHolder, position: Int) {
        holder.bind(homeFashionData[position])
    }

    override fun getItemCount(): Int {
        return homeFashionData.size
    }

    inner class HomeChildViewHolder(private var layoutHomeChildItemBinding: LayoutHomeChildItemBinding) :
        RecyclerView.ViewHolder(layoutHomeChildItemBinding.root), ViewClickCallback {

        fun bind(homeFashionData: HomeFashionData) {
            layoutHomeChildItemBinding.viewHandler = this
            layoutHomeChildItemBinding.data = homeFashionData

            loadImage(homeFashionData.businessPicture)
        }

        @SuppressLint("CheckResult")
        private fun loadImage(url: String) {
            Glide.with(layoutHomeChildItemBinding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutHomeChildItemBinding.progressBarMedium?.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        layoutHomeChildItemBinding.progressBarMedium?.visibility = View.GONE
                        return false
                    }
                }).into(layoutHomeChildItemBinding.itemImage!!)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.rl_product -> {
                    clickHandler.onClick(0, homeFashionData[adapterPosition], 0)
                }
            }
        }

    }
}