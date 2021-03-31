package com.io.app.shakomako.ui.chat.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.ChatMessageData
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.application.ShakoMakoApplication
import com.io.app.shakomako.databinding.ActivityChatBinding
import com.io.app.shakomako.databinding.LayoutChatOptionsBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.chat.adapter.ChatMessageAdapter
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.TEXT
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : DataBindingActivity<ActivityChatBinding>(), ViewClickCallback {

    lateinit var viewModel: ChatViewModel
    lateinit var adapter: ChatMessageAdapter
    lateinit var socket: Socket
    lateinit var dialogFragment: BottomSheetDialog

    override fun layoutRes(): Int {
        return R.layout.activity_chat
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(ChatViewModel::class.java)

        init()
    }

    private fun init() {
        socket = ShakoMakoApplication.appInstance.getSocket()
        connectToSocket()

        dataBinding.viewModel = viewModel
        dataBinding.viewHandler = this

        viewModel.observer.chatType = intent.getIntExtra(AppConstant.TYPE, 0)
        if (viewModel.observer.chatType == AppConstant.PERSONAL_CHAT) {
            viewModel.observer.setPersonalData(intent.getSerializableExtra(AppConstant.PARCEL_DATA) as PersonalChatResponse)
        } else viewModel.observer.setBusinessData(intent.getSerializableExtra(AppConstant.PARCEL_DATA) as BusinessChatResponse)

        adapter = ChatMessageAdapter(this, viewModel.observer.chatType)
        dataBinding.rvChat.adapter = adapter

        dataBinding.etMessage.addTextChangedListener(textListener)
        dataBinding.etMessage.setOnFocusChangeListener { _, _ ->
            Log.e("TAG", " focus change")
            dataBinding.rvChat.scrollToPosition(
                adapter.itemCount - 1
            )
        }



        loadImage(viewModel.observer.picture)
        getAllChats()
    }

    private fun getAllChats() {
        viewModel.getAllChats(apiListener(), viewModel.observer.roomId)
            .observe(this, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        val list = mutableListOf<ChatMessageData>()
                        list.addAll(response.body ?: ArrayList())
                        list.reverse()
                        adapter.list = list as ArrayList<ChatMessageData>
                        dataBinding.rvChat.scrollToPosition(adapter.itemCount - 1)
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    @SuppressLint("CheckResult")
    private fun loadImage(url: String) {
        Glide.with(dataBinding.root).load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    dataBinding.progressBarMedium.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    dataBinding.progressBarMedium.visibility = View.GONE
                    return false
                }
            }).into(dataBinding.userImage)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_send -> {
                if (viewModel.observer.message.isEmpty()) {
                    return
                }
                sendMessage(viewModel.observer.message, TEXT, "", "")
            }

            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.iv_plus -> {
                showBottomSheet()
            }
        }
    }


    /*----------------------------------------------------------------------------------------------*/
    /*----------------------------------------Socket Implementation --------------------------------*/
    /*----------------------------------------------------------------------------------------------*/


    private fun connectToSocket() {
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on("room_join_response", roomJoinListener)
        socket.on("message_response", messageListener)
        socket.connect()
    }

    private val onConnectError =
        Emitter.Listener { args: Array<Any?>? ->
            runOnUiThread {
                Log.e(
                    "TAG",
                    " Connection Error " + Arrays.toString(args)
                )
            }
        }

    private val onConnect: Emitter.Listener = Emitter.Listener { args ->
        run {
            Log.e("TAG", "onConnect")
            socket.emit("room_join", createDataRoomJoin())
        }

    }

    private val roomJoinListener = Emitter.Listener {}

    private val messageListener =
        Emitter.Listener { args ->
            run {
                val messageJson = args[0] as JSONObject
                val data = ChatMessageData()
                data.message = messageJson.getString("message")
                data.sender = messageJson.getString("sender")
                data.type = messageJson.getString("type")
                data.attachment = messageJson.getString("attachment")
                data.seen = messageJson.getString("seen")
                data.price = messageJson.getString("price")
                data.room_id = messageJson.getInt("room_id")
                data.message_id = messageJson.getInt("message_id")
                runOnUiThread {
                    adapter.addMessage(data)
                    viewModel.observer.message = ""
                    dataBinding.rvChat.scrollToPosition(adapter.itemCount - 1)
                }

                Log.e("TAG", "Message Listener : $messageJson")
            }
        }

    private fun createDataRoomJoin(): JSONObject {
        val `object` = JSONObject()
        `object`.put("user_id", viewModel.observer.userId)
        `object`.put("room_id", viewModel.observer.roomId)
        `object`.put("business_id", viewModel.observer.businessId)
        return `object`
    }

    private fun sendMessage(
        message: String,
        type: String,
        attachment: String,
        price: String
    ) {
        try {
            val json = createDataSendMessage(message, type, attachment, price)
            socket.emit("messages", json)
            Log.e("TAG", "Message Send : $json")
        } catch (e: Exception) {
            Log.e("TAG", " Message Send Error : ${e.localizedMessage}")
        }

    }

    private fun createDataSendMessage(
        message: String,
        type: String,
        attachment: String,
        price: String
    ): JSONObject {
        val `object` = JSONObject()
        `object`.put("room_id", viewModel.observer.roomId)
        `object`.put("sender", viewModel.observer.sender)
        `object`.put("message", message)
        `object`.put("type", type)
        `object`.put("attachment", attachment)
        `object`.put("price", price)

        return `object`
    }


    override fun onDestroy() {
        super.onDestroy()
        socket.off(Socket.EVENT_CONNECT)
        socket.off(Socket.EVENT_CONNECT_ERROR)
        socket.close()
    }


    /*----------------------------------------------------------------------------------------------*/
    /*----------------------------------------Text Listener ----------------------------------------*/
    /*----------------------------------------------------------------------------------------------*/

    private var textListener: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }


    /*----------------------------------------------------------------------------------------------*/
    /*----------------------------------------Bottom Sheet------------------------------------------*/
    /*----------------------------------------------------------------------------------------------*/

    private fun showBottomSheet() {
        dialogFragment = BottomSheetDialog(this, R.style.Widget_ShakoMako_BottomSheetStyle)
        val bottomSheetBinding: LayoutChatOptionsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.layout_chat_options,
            null,
            false
        )
        bottomSheetBinding.isPersonal = viewModel.observer.chatType == AppConstant.PERSONAL_CHAT

        bottomSheetBinding.viewHandler = object : ViewClickCallback {
            override fun onClick(v: View) {

            }
        }
        dialogFragment.setContentView(bottomSheetBinding.root)
        dialogFragment.show()
    }

    private fun hideBottomSheet() {
        try {
            dialogFragment.dismiss()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}