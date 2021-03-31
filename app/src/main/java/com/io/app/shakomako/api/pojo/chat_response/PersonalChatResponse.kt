package com.io.app.shakomako.api.pojo.chat_response

import java.io.Serializable

data class PersonalChatResponse(
    val created_at: String = "",
    val lastMessage: String = "",
    val type: String = "",
    val businessVerificationStatus: String = "",
    val room_id: Int = 0,
    val seen: String = "",
    val business_id: Int = 0,
    val business_picture: String = "",
    val business_name: String = ""
): Serializable