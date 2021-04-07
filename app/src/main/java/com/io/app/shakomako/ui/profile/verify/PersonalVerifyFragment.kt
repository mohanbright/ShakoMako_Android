package com.io.app.shakomako.ui.profile.verify

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.verification.PersonalVerificationData
import com.io.app.shakomako.api.pojo.verification.PersonalVerifySubmission
import com.io.app.shakomako.databinding.PersonalVerifyFragmentBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.session.SessionConstants

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
        Glide.with(viewDataBinding.root)
            .load(viewModel.userSession.getStringValue(SessionConstants.USER_PROFILE))
            .into(viewDataBinding.profileImage)

        getPersonalVerificationData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.tv_submit -> {

            }
        }
    }

    private fun getPersonalVerificationData() {
        viewModel.getPersonalVerificationData(apiListener())
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        val data = response.body ?: PersonalVerificationData()
                        viewDataBinding.data = data
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun submitPersonalVerification(){
//        var data = PersonalVerifySubmission(viewDataBinding.data.customer_id, viewDataBinding.data.)
//        viewModel.userVerifySubmission(apiListener(),data)
    }
}