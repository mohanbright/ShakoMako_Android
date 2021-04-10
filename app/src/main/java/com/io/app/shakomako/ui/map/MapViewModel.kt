package com.io.app.shakomako.ui.map

import android.content.Context
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.session.UserSession
import javax.inject.Inject

class MapViewModel @Inject constructor(context: Context, apiRepository: ApiRepository, var userSession: UserSession) : BaseViewModel() {

    init {
        this.apiRepository = apiRepository
        this.context = context
    }
}