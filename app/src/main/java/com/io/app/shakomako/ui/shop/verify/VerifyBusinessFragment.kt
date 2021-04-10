package com.io.app.shakomako.ui.shop.verify

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.verification.BusinessVerificationData
import com.io.app.shakomako.databinding.VerifyBusinessFragmentBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.session.SessionConstants

class VerifyBusinessFragment : HomeBaseFragment<VerifyBusinessFragmentBinding>(),
    ViewClickCallback {

    override fun layoutRes(): Int {
        return R.layout.verify_business_fragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this

        getBusinessVerificationData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.tv_submit -> {
                businessVerifySubmission()
            }
        }
    }

    private fun getBusinessVerificationData() {
        viewModel.getBusinessVerificationData(apiListener())
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        val data = response.body ?: BusinessVerificationData()
                        Glide.with(viewDataBinding.root).load(data.business_picture)
                            .into(viewDataBinding.profileImage)
                        viewDataBinding.data = (data)
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

    private fun businessVerifySubmission() {
        viewModel.businessVerifySubmission(
            apiListener(),
            viewModel.userSession.getIntValue(SessionConstants.BUSINESS_ID)
        ).observe(viewLifecycleOwner,
            Observer {
                showToast(
                    it.message ?: resources.getString(R.string.msg_something_went_wrong)
                )
            })
    }


}