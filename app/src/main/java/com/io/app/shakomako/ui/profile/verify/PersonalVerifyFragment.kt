package com.io.app.shakomako.ui.profile.verify

import android.os.Bundle
import android.view.View
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.PersonalVerifyFragmentBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.home.HomeBaseFragment

class PersonalVerifyFragment : HomeBaseFragment<PersonalVerifyFragmentBinding>(),
    ViewClickCallback {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    override fun layoutRes(): Int {
        return R.layout.personal_verify_fragment
    }

    private fun init() {
        viewDataBinding.viewHandler = this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

}