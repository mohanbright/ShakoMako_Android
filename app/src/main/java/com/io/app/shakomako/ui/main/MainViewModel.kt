package com.io.app.shakomako.ui.main

import android.content.Context
import android.util.Log
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.login.LoginRequest
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.session.UserSession
import org.json.JSONObject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    context: Context,
    apiRepository: ApiRepository,
    val userSession: UserSession
) : BaseViewModel() {

    init {
        this.context = context
        this.apiRepository = apiRepository
    }

    var loginRequest: LoginRequest = LoginRequest()
}