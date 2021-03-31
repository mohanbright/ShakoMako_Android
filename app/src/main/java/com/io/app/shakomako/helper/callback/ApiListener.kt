package com.io.app.shakomako.helper.callback

interface ApiListener {
    fun showProgress(isVisible: Boolean)
    fun msg(msg: String)
}