package com.io.app.shakomako.api.pojo.chat_response

import java.io.Serializable

data class BusinessChatResponse(
    val created_at: String = "",
    val lastMessage: String = "",
    val type: String = "",
    val personVerification_status: String = "",
    val room_id: Int = 0,
    val seen: String = "",
    val user_id: Int = 0,
    val user_image: String = "",
    val user_name: String = ""
) : Serializable


