package com.io.app.shakomako.ui.filter.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.LayoutItemBrandBinding
import com.io.app.shakomako.databinding.LayoutMyShopItemBinding

class LayoutItemBrandAdapter(var context: Context) :
    RecyclerView.Adapter<LayoutItemBrandAdapter.MyShopViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyShopViewHolder {
        val layoutItemBrandBinding: LayoutItemBrandBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_item_brand, parent, false
        )

        Log.e("gfjchaks","MyShopItemAdapter")

        return MyShopViewHolder(layoutItemBrandBinding)
    }

    override fun onBindViewHolder(holder: MyShopViewHolder, position: Int) {
        Log.e("gfjchaks","MyShopItemAdapter")

    }

    override fun getItemCount(): Int {
        return 8
    }

    inner class MyShopViewHolder(private val layoutItemBrandBinding: LayoutItemBrandBinding) :
        RecyclerView.ViewHolder(layoutItemBrandBinding.root) {

    }
}