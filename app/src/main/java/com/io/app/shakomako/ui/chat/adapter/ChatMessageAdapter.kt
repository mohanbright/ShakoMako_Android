package com.io.app.shakomako.ui.chat.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
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
import com.io.app.shakomako.api.pojo.chat_response.ChatMessageData
import com.io.app.shakomako.databinding.LayoutChatListItemBinding
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.BUSINESS
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.DELIVERY_ADDRESS
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.ICI
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.IMAGE
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.LOCATION
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.PRODUCT
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.TEXT
import kotlin.collections.ArrayList

class ChatMessageAdapter(
    var context: Context,
    var type: Int,
    var clickHandler: RecyclerClickHandler<View, ChatMessageData, Int>
) :
    RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder>() {

    var list: ArrayList<ChatMessageData> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addMessage(data: ChatMessageData) {
        list.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding: LayoutChatListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_chat_list_item,
            parent,
            false
        )
        return ChatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        try {
            holder.bind(list[position])
        } catch (e: ArrayIndexOutOfBoundsException) {
            //todo
        }
    }


    inner class ChatViewHolder(var binding: LayoutChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root), ViewClickCallback {

        lateinit var data: ChatMessageData

        fun bind(data: ChatMessageData) {
            this.data = data
            binding.viewHandler = this
            when (data.type) {
                TEXT -> {
                    if (type == AppConstant.PERSONAL_CHAT || type == AppConstant.CREATE_CHAT)
                        if (data.sender == BUSINESS) {
                            binding.messageType = 1
                        } else {
                            binding.messageType = 0
                        }
                    else
                        if (data.sender == BUSINESS) {
                            binding.messageType = 0
                        } else {
                            binding.messageType = 1
                        }
                }

                ICI -> {
                    if (type == AppConstant.PERSONAL_CHAT || type == AppConstant.CREATE_CHAT)
                        if (data.sender == BUSINESS)
                            binding.messageType = 3
                        else
                            binding.messageType = 2
                    else
                        if (data.sender == BUSINESS)
                            binding.messageType = 2
                        else
                            binding.messageType = 3
                }

                PRODUCT -> {
                    if (type == AppConstant.PERSONAL_CHAT || type == AppConstant.CREATE_CHAT)
                        if (data.sender == BUSINESS) {
                            binding.messageType = 5
                            loadImageTwo(data.attachment)
                        } else {
                            binding.messageType = 4
                            loadImage(data.attachment)
                        }
                    else
                        if (data.sender == BUSINESS) {
                            binding.messageType = 4
                            loadImage(data.attachment)
                        } else {
                            binding.messageType = 5
                            loadImageTwo(data.attachment)
                        }
                }

                DELIVERY_ADDRESS, LOCATION -> {
                    if (type == AppConstant.PERSONAL_CHAT || type == AppConstant.CREATE_CHAT)
                        if (data.sender == BUSINESS)
                            binding.messageType = 7
                        else
                            binding.messageType = 6
                    else
                        if (data.sender == BUSINESS)
                            binding.messageType = 6
                        else
                            binding.messageType = 7
                }

                IMAGE -> {
                    if (type == AppConstant.PERSONAL_CHAT || type == AppConstant.CREATE_CHAT)
                        if (data.sender == BUSINESS) {
                            binding.messageType = 9
                            loadImageTwo(data.attachment)
                        } else {
                            binding.messageType = 8
                            loadImage(data.attachment)
                        }
                    else
                        if (data.sender == BUSINESS) {
                            binding.messageType = 8
                            loadImage(data.attachment)
                        } else {
                            binding.messageType = 9
                            loadImageTwo(data.attachment)
                        }
                }
            }
            binding.data = data
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
                }).into(binding.ivProduct)
        }

        @SuppressLint("CheckResult")
        private fun loadImageTwo(url: String) {
            Glide.with(binding.root).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBarMediumTwo.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBarMediumTwo.visibility = View.GONE
                        return false
                    }
                }).into(binding.ivProductTwo)
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.ll_invoice_send -> {
                    clickHandler.onClick(v, data, adapterPosition)
                }
            }
        }
    }


}