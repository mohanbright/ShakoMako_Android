package com.io.app.shakomako.ui.base

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.ui.home.HomeActivity

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

    fun isHomeActivity(isVisible: Boolean) {
        try {
            (getBaseActivity() as HomeActivity).isHomeActivity(isVisible)
        } catch (E: Exception) {
            //todo
        }
    }

    fun openFragment(type: Int) {
        try {
            (getBaseActivity() as HomeActivity).openFragment(type)
        } catch (E: Exception) {
            //todo
        }
    }

    fun openFragmentWithData(id: Int, bundle: Bundle) {
        try {
            (getBaseActivity() as HomeActivity).openFragmentWithData(id, bundle)
        } catch (E: Exception) {
            //todo
        }
    }

    protected inline fun <reified T : ViewModel> bindViewModel(
        noinline f: (TBinding.(T) -> Unit)? = null
    ): T {
        val viewModel = getBaseActivity().getViewModel(T::class.java)
        f?.invoke(viewDataBinding, viewModel)
        viewDataBinding.executePendingBindings()
        return viewModel
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
        val baseActivity = getThisActivity()
        baseActivity.startNewActivity(className)
    }

    override fun showToast(msg: String) {
        getBaseActivity().showToast(msg)
    }

    override fun onBackPressed() {
        getBaseActivity().onBackPressed()
    }

    override fun finishAffinity() {
        getBaseActivity().finishAffinity()
    }

    override fun apiListener(): ApiListener {
        return getBaseActivity().apiListener()
    }

    override fun openSingleImagePicker(dataItemCallback: DataItemCallBack<Uri, Int>) {
        getBaseActivity().openSingleImagePicker(dataItemCallback)
    }

    override fun openMultipleImagePicker(dataItemCallback: DataItemCallBack<List<Uri>, Int>) {
        getBaseActivity().openMultipleImagePicker(dataItemCallback)
    }

    override fun checkThePermission(
        title: String,
        deniedMsg: String,
        dataItemCallback: DataItemCallBack<Int, Int>,
        vararg permissions: String
    ) {
        getBaseActivity().checkThePermission(title, deniedMsg, dataItemCallback, *permissions)
    }

    override fun hideKeyboard() {
        getBaseActivity().hideKeyboard()
    }
}