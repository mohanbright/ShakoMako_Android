package com.io.app.shakomako.ui.base

import android.app.Activity

interface BaseHandler {


    abstract fun getThisActivity() : BaseActivity

    fun <T:Activity> startNewActivity(className: Class<T>)



}