package com.io.app.shakomako.ui.search

import android.content.Context
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.ui.base.BaseViewModel
import com.io.app.shakomako.utils.session.UserSession
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    context: Context,
    apiRepository: ApiRepository,
    var userSession: UserSession
) :
    BaseViewModel() {


    init {
        this.context = context
        this.apiRepository = apiRepository
    }





}
