package com.io.app.shakomako.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.home.HomeItem
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData
import com.io.app.shakomako.databinding.LayoutHomeItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.RecyclerDataCallBack
import com.io.app.shakomako.helper.callback.ViewClickCallback

class HomeAdapter(
    val context: Context,
    var clickHandler: RecyclerDataCallBack<Int, HomeItem, HomeFashionData, Int>
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var list: ArrayList<HomeItem> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val layoutHomeItemBinding: LayoutHomeItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_home_item,
                parent,
                false
            )
        return HomeViewHolder(layoutHomeItemBinding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class HomeViewHolder(private val layoutHomeItemBinding: LayoutHomeItemBinding) :
        RecyclerView.ViewHolder(layoutHomeItemBinding.root), ViewClickCallback {

        fun bind(item: HomeItem) {
            layoutHomeItemBinding.data = item
            layoutHomeItemBinding.viewHandler = this
            val adapter =
                HomeChildAdapter(context, object : RecyclerClickHandler<Int, HomeFashionData, Int> {
                    override fun onClick(k: Int, l: HomeFashionData, m: Int) {
                        clickHandler.onClick(k, list[adapterPosition], l, m)
                    }
                })
            adapter.homeFashionData = item.list
            layoutHomeItemBinding.rvHomeChild.adapter = adapter
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.tv_product_type -> {
                    clickHandler.onClick(
                        1,
                        list[adapterPosition],
                        HomeFashionData(),
                        adapterPosition
                    )
                }
            }
        }
    }


}