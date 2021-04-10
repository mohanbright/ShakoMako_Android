package com.io.app.shakomako.api.pojo.home

import androidx.databinding.BaseObservable
import com.io.app.shakomako.api.pojo.home.item.HomeFashionData

class HomeItem(var name: String = "", var list: ArrayList<HomeFashionData> = ArrayList()) :
    BaseObservable() {
}