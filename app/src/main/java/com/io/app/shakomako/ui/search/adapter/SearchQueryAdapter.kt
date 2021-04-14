package com.io.app.shakomako.ui.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.search.SearchQueryResponse
import com.io.app.shakomako.databinding.ItemSearchBinding

class SearchQueryAdapter : RecyclerView.Adapter<SearchQueryAdapter.SearchViewHolder>() {

    var searchItemList: List<SearchQueryResponse> = ArrayList()

    fun addList(list: List<SearchQueryResponse>) {
        searchItemList = ArrayList()
        Log.e("list","size is : ${list.size}")
        searchItemList = list
        notifyDataSetChanged()
    }


    inner class SearchViewHolder(var itemSearchBinding: ItemSearchBinding) :
        RecyclerView.ViewHolder(itemSearchBinding.root) {
        fun bind(pos: Int) {
            itemSearchBinding.data = searchItemList[pos]

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchQueryAdapter.SearchViewHolder {
        val itemSearchBinding: ItemSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_search, parent, false
        )
        return SearchViewHolder(itemSearchBinding)

    }

    override fun getItemCount(): Int = searchItemList.size

    override fun onBindViewHolder(holder: SearchQueryAdapter.SearchViewHolder, position: Int) {
        holder.bind(position)

    }
}