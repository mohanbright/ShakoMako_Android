package com.io.app.shakomako.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.databinding.LayoutBusinessChatItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.utils.constants.MessageConstant

class BusinessChatAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<Int, BusinessChatResponse, Int>
) :
    RecyclerView.Adapter<BusinessChatAdapter.BusinessChatViewHolder>() {

    var businessChatList = ArrayList<BusinessChatResponse>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessChatViewHolder {
        val layoutBusinessChatItemBinding: LayoutBusinessChatItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_business_chat_item, parent, false
        )
        return BusinessChatViewHolder(layoutBusinessChatItemBinding)
    }


    override fun onBindViewHolder(holder: BusinessChatViewHolder, position: Int) {
        holder.bind(businessChatList[position])
    }


    override fun getItemCount(): Int =
        if (businessChatList.isNullOrEmpty()) 0 else businessChatList.size

    inner class BusinessChatViewHolder(private var layoutBusinessChatItemBinding: LayoutBusinessChatItemBinding) :
        RecyclerView.ViewHolder(layoutBusinessChatItemBinding.root) {

        fun bind(data: BusinessChatResponse) {
            layoutBusinessChatItemBinding.businessdata = data
            when (data.type) {
                MessageConstant.IMAGE -> {
                    layoutBusinessChatItemBinding.tvBusinessMessage.text =
                        "Image Sent"
                }
                MessageConstant.TEXT -> {
                    layoutBusinessChatItemBinding.tvBusinessMessage.text =
                        data.lastMessage
                }

                MessageConstant.PRODUCT -> {
                    layoutBusinessChatItemBinding.tvBusinessMessage.text =
                        "Product sent"
                }
                MessageConstant.LOCATION -> {
                    layoutBusinessChatItemBinding.tvBusinessMessage.text =
                        data.lastMessage
                }
                MessageConstant.DELIVERY_ADDRESS -> {
                    layoutBusinessChatItemBinding.tvBusinessMessage.text =
                        data.lastMessage
                }
                MessageConstant.ICI -> {
                    layoutBusinessChatItemBinding.tvBusinessMessage.text =
                        "In-Chat-Invoice Generated"
                }
            }
            Glide.with(layoutBusinessChatItemBinding.root.context).load(data.user_image)
                .into(layoutBusinessChatItemBinding.profileImage)

            layoutBusinessChatItemBinding.root.setOnClickListener {
                clickHandler.onClick(
                    0,
                    data,
                    adapterPosition
                )
            }
        }
    }
}