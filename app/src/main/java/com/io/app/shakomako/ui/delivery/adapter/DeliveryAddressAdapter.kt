package com.io.app.shakomako.ui.delivery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.address.DeliveryAddress
import com.io.app.shakomako.databinding.LayoutDeliveryAddressItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback

class DeliveryAddressAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<Int, DeliveryAddress, Int>
) :
    RecyclerView.Adapter<DeliveryAddressAdapter.DeliveryViewHolder>() {

    var list: List<DeliveryAddress> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DeliveryViewHolder(var layoutDeliveryAddressItemBinding: LayoutDeliveryAddressItemBinding) :
        RecyclerView.ViewHolder(layoutDeliveryAddressItemBinding.root), ViewClickCallback {

        fun bind(data: DeliveryAddress) {
            layoutDeliveryAddressItemBinding.data = data
            layoutDeliveryAddressItemBinding.viewHandler = this
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.iv_edit -> {
                    clickHandler.onClick(0, list[adapterPosition], adapterPosition)
                }

                R.id.tv_delete->{
                    clickHandler.onClick(1, list[adapterPosition], adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val layoutDeliveryAddressItemBinding: LayoutDeliveryAddressItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.layout_delivery_address_item, parent, false
            )

        return DeliveryViewHolder(layoutDeliveryAddressItemBinding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}