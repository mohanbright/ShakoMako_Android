package com.io.app.shakomako.ui.notification

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivityNotificationBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity

class NotificationActivity : DataBindingActivity<ActivityNotificationBinding>(), ViewClickCallback {

    lateinit var viewModel: NotificationViewModel

    override fun layoutRes(): Int {
        return R.layout.activity_notification
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(NotificationViewModel::class.java)
        init()
    }

    private fun init() {
        dataBinding.viewHandler = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBackPressed()
        }
    }
}