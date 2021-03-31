package com.io.app.shakomako.helper.callback

interface DataItemCallBack<K, L> {
    fun onItemData(t: K?, r: L?)
}