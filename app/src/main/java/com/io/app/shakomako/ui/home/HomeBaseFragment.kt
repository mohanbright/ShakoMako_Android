package com.io.app.shakomako.ui.home

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.io.app.shakomako.ui.base.BaseFragment

abstract class HomeBaseFragment<Binds : ViewDataBinding> : BaseFragment<Binds>() {
    protected lateinit var viewModel: HomeViewModel

    @JvmName("setViewModel1")
    fun setUpViewModel() {
        viewModel = (getBaseActivity() as HomeActivity).callHomeViewModel()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
    }

}