package com.io.app.shakomako.ui.trending.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ItemTrendingBinding


class TrendingAdapter(var mList:List<String>) : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
         parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var itemTrendingBinding =DataBindingUtil.inflate<ItemTrendingBinding>(LayoutInflater.from(parent.context),R.layout.item_trending,parent,false);

        return ViewHolder(itemTrendingBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val pos = position % mList.size
        Log.e("imageUrl","${mList[pos]}")
        holder.bind(mList[pos])
    }

    override fun getItemCount(): Int {
        return mList.size * 4
    }

    // Initializing the Views
    class ViewHolder(var itemTrendingBinding: ItemTrendingBinding) : RecyclerView.ViewHolder(itemTrendingBinding.root) {
        fun bind(imageUrl: String){
            Log.e("imageUrl","bind $imageUrl}")
            Glide.with(itemTrendingBinding.root.context).load(imageUrl).placeholder(R.drawable.ic_cart).into(itemTrendingBinding.imageview)
            val lp = itemTrendingBinding.imageview.layoutParams
            if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1f
            }
        }

    }

    }

