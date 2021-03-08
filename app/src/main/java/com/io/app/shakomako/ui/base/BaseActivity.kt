package com.io.app.shakomako.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.app.shakomako.dagger.factory.BaseViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), BaseHandler {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory


    protected open fun <T : ViewModel?> getViewModel(viewModel: Class<T>?): T {
        return ViewModelProvider(this, viewModelFactory)[viewModel!!]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getThisActivity(): BaseActivity = this@BaseActivity

    override fun <T : Activity> startNewActivity(java: Class<T>) {
        val intent = Intent(this, java)
        startActivity(intent)
        finish()
    }


}