package com.io.app.shakomako.api.pojo.chat_response

import java.io.Serializable

data class PersonalChatResponse(
    var created_at: String = "",
    var lastMessage: String = "",
    var type: String = "",
    var businessVerificationStatus: String = "",
    var room_id: Int = 0,
    var seen: String = "",
    var business_id: Int = 0,
    var business_picture: String = "",
    var business_name: String = ""
): Serializable