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
import com.io.app.shakomako.databinding.LayoutPersonalChatItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.utils.constants.MessageConstant

class PersonalChatAdapter(
    var context: Context,
    var clickHandler: RecyclerClickHandler<Int, PersonalChatResponse, Int>
) :
    RecyclerView.Adapter<PersonalChatAdapter.PersonalChatViewHandler>() {

    var personalChatList = ArrayList<PersonalChatResponse>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class PersonalChatViewHandler(var layoutPersonalChatItemBinding: LayoutPersonalChatItemBinding) :
        RecyclerView.ViewHolder(layoutPersonalChatItemBinding.root) {

        fun bind(data: PersonalChatResponse) {
            layoutPersonalChatItemBinding.personalData = data
            when(data.type){
                MessageConstant.IMAGE-> layoutPersonalChatItemBinding.tvMessage.text = "Image Sent"
                MessageConstant.TEXT-> layoutPersonalChatItemBinding.tvMessage.text = data.lastMessage
                MessageConstant.PRODUCT-> layoutPersonalChatItemBinding.tvMessage.text = "Product sent"
                MessageConstant.LOCATION-> layoutPersonalChatItemBinding.tvMessage.text = data.lastMessage
                MessageConstant.DELIVERY_ADDRESS-> layoutPersonalChatItemBinding.tvMessage.text = data.lastMessage
            }

            Glide.with(layoutPersonalChatItemBinding.root.context).load(data.business_picture)
                .into(layoutPersonalChatItemBinding.profileImage)

            layoutPersonalChatItemBinding.root.setOnClickListener {
                clickHandler.onClick(
                    0,
                    data,
                    adapterPosition
                )
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalChatViewHandler {
        val layoutPersonalChatItemBinding: LayoutPersonalChatItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_personal_chat_item, parent, false
        )

        return PersonalChatViewHandler(layoutPersonalChatItemBinding)
    }

    override fun onBindViewHolder(holder: PersonalChatViewHandler, position: Int) {
        holder.bind(personalChatList[position])
    }

    override fun getItemCount(): Int {
        return if (personalChatList.isNullOrEmpty()) 0 else personalChatList.size
    }
}