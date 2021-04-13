package com.io.app.shakomako.api.pojo.chathistory

import androidx.databinding.BaseObservable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckChatHistoryResponse : BaseObservable() {

    @SerializedName("room_id")
    @Expose
    var roomId: Int = 0
        set(value) {
            field = value
            notifyChange()
        }
}