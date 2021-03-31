package com.io.app.shakomako.ui.base

import android.app.Activity
import android.net.Uri
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.DataItemCallBack

interface BaseHandler {


    abstract fun getThisActivity(): BaseActivity

    fun <T : Activity> startNewActivity(className: Class<T>)

    fun showToast(msg: String)

    fun onBackPressed()

    fun finishAffinity()

    fun apiListener(): ApiListener

    fun openSingleImagePicker(dataItemCallback: DataItemCallBack<Uri, Int>)

    fun openMultipleImagePicker(dataItemCallback: DataItemCallBack<List<Uri>, Int>)

    fun checkThePermission(
        title: String,
        deniedMsg: String,
        dataItemCallback: DataItemCallBack<Int, Int>,
        vararg permissions: String
    )

    fun hideKeyboard()

}