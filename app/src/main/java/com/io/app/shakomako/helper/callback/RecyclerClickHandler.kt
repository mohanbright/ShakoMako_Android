package com.io.app.shakomako.helper.callback

interface RecyclerClickHandler<K, L, M> {
    fun onClick(k: K, l: L, m: M)
}