package com.io.app.shakomako.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<TBinding : ViewDataBinding> : Fragment(), BaseHandler {

    lateinit var viewDataBinding: TBinding


    @LayoutRes
    protected abstract fun layoutRes(): Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root

    }


    open fun getBaseActivity(): BaseActivity {
        val activity = activity
        if (activity is BaseActivity) {
            return activity
        }
        throw RuntimeException("BaseActivity is null")
    }

    override fun getThisActivity(): BaseActivity = getBaseActivity()

    override fun <T : Activity> startNewActivity(className: Class<T>) {
        val baseActivty = getThisActivity()
        baseActivty.startNewActivity(className)
    }


}