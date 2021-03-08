package com.io.app.shakomako.ui.splash

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivitySplashBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseActivity
import com.io.app.shakomako.ui.base.DataBindingActivty

class SplashActivity : DataBindingActivty<ActivitySplashBinding>(),ViewClickCallback {


    override fun layoutRes(): Int = R.layout.activity_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplashActivity()
    }

    fun initSplashActivity() {

    }


    override fun getThisActivity(): BaseActivity = this@SplashActivity
    override fun onClick(v: View) {
        when(v.id){

        }

    }

}