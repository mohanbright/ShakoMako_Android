package com.io.app.shakomako.ui.chat.activity

import android.content.Context
import androidx.databinding.BaseObservable
import bolts.Bolts
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.ChatMessageData
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.api.pojo.product.ProductResponse
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.session.SessionConstants
import com.io.app.shakomako.utils.session.UserSession
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    context: Context,
    apiRepository: ApiRepository,
    var userSession: UserSession
) : BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    var observer: ChatObserver = ChatObserver()
    var allMessageList: List<ChatMessageData> = ArrayList()

    inner class ChatObserver : BaseObservable() {
        var chatType: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var createdAt: String = ""
            set(value) {
                field = value
                notifyChange()
            }
        var lastMessage: String = ""
            set(value) {
                field = value
                notifyChange()
            }
        var type: String = ""
            set(value) {
                field = value
                notifyChange()
            }
        var businessVerificationStatus: String = ""
            set(value) {
                field = value
                notifyChange()
            }
        var roomId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }
        var seen: String = ""
            set(value) {
                field = value
                notifyChange()
            }
        var businessId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }
        var picture: String = ""
            set(value) {
                field = value
                notifyChange()
            }
        var name: String = ""
            set(value) {
                field = value
                notifyChange()
            }

        var userId: Int = userSession.getIntValue(SessionConstants.USER_ID)

        var sender: String = ""
            set(value) {
                field = value
                notifyChange()
            }

        var message: String = ""
            set(value) {
                field = value
                notifyChange()
            }

        var productResponse: ProductResponse = ProductResponse()
            set(value) {
                field = value
                notifyChange()
            }

        var invoiceId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }

        var isInvoiceRequest: Boolean = false
            set(value) {
                field = value
                notifyChange()
            }

        var latestInvoiceId: Int = 0
            set(value) {
                field = value
                notifyChange()
            }


        fun setPersonalData(data: PersonalChatResponse) {
            createdAt = data.created_at
            lastMessage = data.lastMessage
            type = data.type
            businessVerificationStatus = data.businessVerificationStatus
            roomId = data.room_id
            seen = data.seen
            businessId = data.business_id
            name = data.business_name
            picture = data.business_picture
            sender = "user"
        }

        fun setBusinessData(data: BusinessChatResponse) {
            createdAt = data.created_at
            lastMessage = data.lastMessage
            type = data.type
            businessVerificationStatus = data.personVerification_status
            roomId = data.room_id
            seen = data.seen
            businessId = data.user_id
            name = data.user_name
            picture = data.user_image
            sender = "business"
        }

        fun setData(data: PersonalChatResponse) {
            createdAt = data.created_at
            lastMessage = data.lastMessage
            type = data.type
            businessVerificationStatus = data.businessVerificationStatus
            roomId = data.room_id
            seen = data.seen
            businessId = data.business_id
            name = data.business_name
            picture = data.business_picture
            sender = "user"
        }
    }
}