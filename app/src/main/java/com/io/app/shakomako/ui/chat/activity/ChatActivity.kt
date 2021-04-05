package com.io.app.shakomako.ui.chat.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.view.menu.ActionMenuItem
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
import com.io.app.shakomako.api.pojo.invoice.ChatInvoiceProductData
import com.io.app.shakomako.api.pojo.invoice.InvoiceData
import com.io.app.shakomako.api.pojo.invoice.InvoiceSubmitData
import com.io.app.shakomako.api.pojo.product.ProductResponse
import com.io.app.shakomako.api.pojo.upload.UploadResponse
import com.io.app.shakomako.application.ShakoMakoApplication
import com.io.app.shakomako.databinding.ActivityChatBinding
import com.io.app.shakomako.databinding.LayoutChatOptionsBinding
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.chat.adapter.ChatMessageAdapter
import com.io.app.shakomako.ui.invoice.ChatInvoiceActivity
import com.io.app.shakomako.ui.map.MapActivity
import com.io.app.shakomako.utils.ContextUtils
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.MessageConstant
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.PRODUCT
import com.io.app.shakomako.utils.constants.MessageConstant.Companion.TEXT
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : DataBindingActivity<ActivityChatBinding>(), ViewClickCallback {

    lateinit var viewModel: ChatViewModel
    lateinit var adapter: ChatMessageAdapter
    lateinit var socket: Socket
    lateinit var dialogFragment: BottomSheetDialog

    companion object {

        const val CAMERA_REQUEST = 1001

        const val INVOICE_REQUEST = 1002
    }

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
        when (viewModel.observer.chatType) {
            AppConstant.PERSONAL_CHAT -> viewModel.observer.setPersonalData(
                intent.getSerializableExtra(
                    AppConstant.PARCEL_DATA
                ) as PersonalChatResponse
            )
            AppConstant.BUSINESS_CHAT -> viewModel.observer.setBusinessData(
                intent.getSerializableExtra(
                    AppConstant.PARCEL_DATA
                ) as BusinessChatResponse
            )

            AppConstant.CREATE_CHAT -> {
                viewModel.observer.setData(
                    intent.getSerializableExtra(
                        AppConstant.PARCEL_DATA
                    ) as PersonalChatResponse
                )

                viewModel.observer.productResponse =
                    intent.getSerializableExtra(AppConstant.EXTRA_DATA) as ProductResponse

                sendMessage(
                    viewModel.observer.lastMessage,
                    PRODUCT,
                    viewModel.observer.productResponse.product_images[0],
                    viewModel.observer.productResponse.product_asking_price
                )
            }
        }
        adapter = ChatMessageAdapter(this, viewModel.observer.chatType, recyclerClickHandler)
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

    private var recyclerClickHandler: RecyclerClickHandler<View, ChatMessageData, Int> =
        object : RecyclerClickHandler<View, ChatMessageData, Int> {
            override fun onClick(k: View, l: ChatMessageData, m: Int) {
                when (k.id) {
                    R.id.ll_invoice_send -> {
                        try {
                            getInvoiceById(l.message.toInt())
                        } catch (e: java.lang.Exception) {

                        }
                    }
                }
            }
        }

    private fun getAllChats() {
        viewModel.getAllChats(apiListener(), viewModel.observer.roomId)
            .observe(this, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        getInvoiceId(response.body ?: ArrayList())
                        viewModel.allMessageList = response.body ?: ArrayList()

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

    private fun getInvoiceId(list: List<ChatMessageData>) {
        for (data in list) {
            if (data.type == MessageConstant.ICI) {
                viewModel.observer.latestInvoiceId = data.message.toInt()
                Log.e("TAG", "${viewModel.observer.latestInvoiceId}")
                break
            }
        }
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

            R.id.iv_ici -> {
                getInvoiceById(viewModel.observer.latestInvoiceId)
            }
        }
    }

    private fun getInvoiceById(id: Int) {
        viewModel.getInvoiceById(apiListener(), id)
            .observe(this,
                Observer { response ->
                    run {
                        if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                            startActivity(
                                Intent(
                                    getThisActivity(),
                                    ChatInvoiceActivity::class.java
                                ).putExtra(
                                    AppConstant.TYPE,
                                    AppConstant.VIEW_INVOICE_BUSINESS
                                ).putExtra(
                                    AppConstant.PARCEL_DATA,
                                    (response.body ?: InvoiceData())
                                )
                            )
                        } else showToast(
                            response.message
                                ?: resources.getString(R.string.msg_something_went_wrong)
                        )
                    }
                })
    }


    /*----------------------------------------------------------------------------------------------*/
    /*----------------------------------------Socket Implementation --------------------------------*/
    /*----------------------------------------------------------------------------------------------*/


    private fun connectToSocket() {
        socket.on(Socket.EVENT_CONNECT, onConnect)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on("room_join_response", roomJoinListener)
        socket.on("message_response", messageListener)
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        socket.connect()
    }

    private val onDisconnect = Emitter.Listener {
        Log.e("TAG", "onDisconnect")
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
            if (viewModel.observer.isInvoiceRequest) {
                sendMessage("${viewModel.observer.invoiceId}", MessageConstant.ICI, "", "")
            }
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
        socket.off(Socket.EVENT_DISCONNECT)
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
                when (v.id) {
                    R.id.ll_share_delivery_address -> {
                        val intent = Intent(getThisActivity(), MapActivity::class.java)
                        startActivityForResult(intent, AppConstant.DELIVERY_ADDRESS)
                    }
                    R.id.issue_in_chat_invoice -> {
                        if (isAddressAvailable())
                            getChatInvoiceProduct()
                        else showToast(resources.getString(R.string.delivery_address_not_shared))
                    }
                    R.id.ll_camera -> {
                        val cameraIntent =
                            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(cameraIntent, CAMERA_REQUEST)
                    }
                    R.id.ll_photo_and_view_library -> {
                        openSingleImagePicker(object : DataItemCallBack<Uri, Int> {
                            override fun onItemData(t: Uri?, r: Int?) {
                                if (r == 0) {
                                    sendImage(t?.path.toString())
                                }
                            }
                        })
                    }
                    R.id.ll_location -> {
                        val intent = Intent(getThisActivity(), MapActivity::class.java)
                        startActivityForResult(intent, AppConstant.LOCATION)
                    }
                    R.id.ll_cash_on_delivery -> {
                    }
                }
                dialogFragment.dismiss()
            }
        }
        dialogFragment.setContentView(bottomSheetBinding.root)
        dialogFragment.show()
    }

    private fun isAddressAvailable(): Boolean {
        for (data in viewModel.allMessageList) {
            if(data.type == MessageConstant.DELIVERY_ADDRESS){
                return true
            }else{
                continue
            }
        }
        return false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.DELIVERY_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                val latLng: String? = data?.getStringExtra("result")
                Log.e("TAG", "$latLng")
                val lat = latLng?.split(",")!![0].toDouble()
                val lng = latLng.split(",")[1].toDouble()
                val address = ContextUtils.getAddressFromLatLng(
                    getThisActivity(),
                    lat, lng
                )

                sendMessage(
                    "${address?.getAddressLine(0)} &CLLocationCoordinate2D(latitude: ${lat}, longitude: ${lng})",
                    MessageConstant.DELIVERY_ADDRESS,
                    "",
                    ""
                )
            }
        } else if (requestCode == AppConstant.LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                val latLng: String? = data?.getStringExtra("result")
                Log.e("TAG", "$latLng")
                val lat = latLng?.split(",")!![0].toDouble()
                val lng = latLng.split(",")[1].toDouble()
                val address = ContextUtils.getAddressFromLatLng(
                    getThisActivity(),
                    lat, lng
                )

                sendMessage(
                    "${address?.getAddressLine(0)} &CLLocationCoordinate2D(latitude: ${lat}, longitude: ${lng})",
                    MessageConstant.LOCATION,
                    "",
                    ""
                )
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data")
            val uri: Uri? = getImageUri(getThisActivity(), photo as Bitmap)
            sendImage("${getRealPathFromURI(uri)}")
            Log.e("TAG", "${getRealPathFromURI(uri)}")
        } else if (requestCode == INVOICE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.observer.invoiceId = data?.getIntExtra(AppConstant.INVOICE_ID, 0) ?: 0
                Log.e("TAG", "${viewModel.observer.invoiceId}")
                sendMessage("${viewModel.observer.invoiceId}", MessageConstant.ICI, "", "")
                //viewModel.observer.isInvoiceRequest = true
            }
        }
    }

    /**
     * Get Uri from the Bitmap Call Details
     * @param inContext
     * @param inImage
     * @return
     */
    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            inImage,
            "${UUID.randomUUID()}",
            null
        )
        return Uri.parse(path)
    }

    private fun getRealPathFromURI(uri: Uri?): String {
        var path = ""
        if (contentResolver != null) {
            val cursor =
                contentResolver.query(uri!!, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }
        return path
    }

    private fun sendImage(path: String) {
        viewModel.upload(apiListener(), path).observe(getThisActivity(),
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        sendMessage(
                            "",
                            MessageConstant.IMAGE,
                            (response.body ?: UploadResponse()).image,
                            ""
                        )
                    } else showToast(
                        response.message
                            ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }


    /*----------------------------------------------------------------------------------------------*/
    /*----------------------------------Getting Invoive Product-------------------------------------*/
    /*----------------------------------------------------------------------------------------------*/


    private fun getChatInvoiceProduct() {
        viewModel.getChatInvoiceProduct(apiListener(), viewModel.observer.roomId).observe(this,
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        val productId = (response.body ?: ArrayList())[0].product_id
                        Log.e("TAG", "$productId")
                        getInvoiceSubmitData(productId)
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun getInvoiceSubmitData(productId: Int) {
        viewModel.getInvoiceSubmitData(
            apiListener(),
            viewModel.observer.userId,
            productId,
            viewModel.observer.roomId
        ).observe(this,
            Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        startActivityForResult(
                            Intent(
                                getThisActivity(),
                                ChatInvoiceActivity::class.java
                            ).putExtra(AppConstant.TYPE, AppConstant.CREATE_INVOICE).putExtra(
                                AppConstant.PARCEL_DATA,
                                (response.body ?: InvoiceSubmitData())
                            ).putExtra(AppConstant.ROOM_ID, viewModel.observer.roomId)
                                .putExtra(AppConstant.BUSINESS_ID, viewModel.observer.businessId),
                            INVOICE_REQUEST
                        )
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

}