package com.io.app.shakomako.api.pojo.chat_response

import java.io.Serializable

data class ChatMessageData(
    var attachment: String = "",
    var created_at: String = "",
    var message: String = "",
    var message_id: Int = 0,
    var price: String = "",
    var room_id: Int = 0,
    var seen: String = "",
    var sender: String = "",
    var type: String = ""
) : Serializable