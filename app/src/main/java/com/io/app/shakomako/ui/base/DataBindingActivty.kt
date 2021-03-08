package com.io.app.shakomako.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class DataBindingActivty<TBinding : ViewDataBinding> : BaseActivity() {

    lateinit var dataBinding: TBinding

    @LayoutRes
    protected abstract fun layoutRes(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutRes())
        dataBinding.lifecycleOwner = this
    }

    /**
     * Creates a [ViewModel] and binds it to the [ViewDataBinding] for this view.
     */
    protected inline fun <reified T : ViewModel> bindViewModel(
        noinline f: (TBinding.(T) -> Unit)? = null
    ): T {
        val viewModel = getViewModel(T::class.java)
        f?.invoke(dataBinding, viewModel)
        dataBinding.executePendingBindings()
        return viewModel
    }


}