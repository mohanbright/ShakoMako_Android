package com.io.app.shakomako.ui.invoice

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivityChatInvoiceBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity

class ChatInvoiceActivity : DataBindingActivity<ActivityChatInvoiceBinding>(), ViewClickCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        dataBinding.viewHandler = this
    }

    override fun layoutRes(): Int {
        return (R.layout.activity_chat_invoice)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_close -> {
                finish()
            }
        }
    }
}