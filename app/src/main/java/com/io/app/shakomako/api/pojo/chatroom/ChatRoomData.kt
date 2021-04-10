package com.io.app.shakomako.api.pojo.chatroom

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChatRoomData : BaseObservable() {

    @SerializedName("room_id")
    @Expose
    var roomId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }
}