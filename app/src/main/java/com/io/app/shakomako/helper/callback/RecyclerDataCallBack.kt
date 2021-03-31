package com.io.app.shakomako.helper.callback

import com.io.app.shakomako.api.pojo.home.item.HomeFashionData

interface RecyclerDataCallBack<K, L, M, N> {
    fun onClick(k: K, l: L, m: M, n: N)
}