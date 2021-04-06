package com.io.app.shakomako.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.chat_response.BusinessChatResponse
import com.io.app.shakomako.api.pojo.chat_response.PersonalChatResponse
import com.io.app.shakomako.databinding.ChatFragmentBinding
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.RecyclerClickHandler
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.chat.activity.ChatActivity
import com.io.app.shakomako.ui.chat.adapter.BusinessChatAdapter
import com.io.app.shakomako.ui.chat.adapter.PersonalChatAdapter
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.constants.AppConstant

class ChatFragment : HomeBaseFragment<ChatFragmentBinding>(), ViewClickCallback {

    lateinit var businessAdapter: BusinessChatAdapter
    lateinit var personalChatAdapter: PersonalChatAdapter
    var personalSize: Int = 0
    var businessSize: Int = 0

    companion object {
        const val DEAL_REQUEST = 888
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    override fun onResume() {
        super.onResume()
        isHomeActivity(false)
    }

    override fun layoutRes(): Int {
        return R.layout.chat_fragment
    }

    private fun init() {
        viewDataBinding.viewHandler = this
        viewDataBinding.viewModel = viewModel
        businessAdapter = BusinessChatAdapter(getBaseActivity(),
            object : RecyclerClickHandler<Int, BusinessChatResponse, Int> {
                override fun onClick(k: Int, l: BusinessChatResponse, m: Int) {
                    startActivityForResult(
                        Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                            AppConstant.PARCEL_DATA,
                            l
                        ).putExtra(AppConstant.TYPE, AppConstant.BUSINESS_CHAT), DEAL_REQUEST
                    )
                }
            })


        personalChatAdapter = PersonalChatAdapter(
            getBaseActivity(),
            object : RecyclerClickHandler<Int, PersonalChatResponse, Int> {
                override fun onClick(k: Int, l: PersonalChatResponse, m: Int) {
                    startActivityForResult(
                        Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                            AppConstant.PARCEL_DATA,
                            l
                        ).putExtra(AppConstant.TYPE, AppConstant.PERSONAL_CHAT), DEAL_REQUEST
                    )
                }
            })
        viewDataBinding.rvPersonalChat?.adapter = personalChatAdapter

        viewDataBinding.rvBusinessChat?.adapter = businessAdapter
        initObserver()
        initPersonalObserver()
    }

    private fun initObserver() {
        viewModel.getBusinessChatList(apiListener()).observe(viewLifecycleOwner, Observer {
            businessAdapter.businessChatList = it as ArrayList<BusinessChatResponse>
            businessSize = it.size
            handleBusinessEmptyVisibility()
        })
    }


    private fun initPersonalObserver() {
        viewModel.getpersonalChatList(apiListener()).observe(viewLifecycleOwner, Observer {
            personalChatAdapter.personalChatList = it as ArrayList<PersonalChatResponse>
            personalSize = it.size
            handlePersonalEmptyVisibility()
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_personal -> {
                handlePersonalEmptyVisibility()
                viewModel.chatObserver.screenObserver = 0
            }

            R.id.tv_business -> {
                handleBusinessEmptyVisibility()
                viewModel.chatObserver.screenObserver = 1
            }

            R.id.ll_my_deal -> openFragment(AppConstant.MY_DEAL)
        }
    }

    private fun handlePersonalEmptyVisibility() {
        Log.e("TAG", "$personalSize")
        viewDataBinding.tvEmptyMessageBusiness?.visibility = GONE
        if (personalSize == 0) {
            viewDataBinding.tvEmptyMessage?.visibility = VISIBLE
        } else viewDataBinding.tvEmptyMessage?.visibility = GONE
    }

    private fun handleBusinessEmptyVisibility() {
        Log.e("TAG", "$businessAdapter")
        viewDataBinding.tvEmptyMessage?.visibility = GONE
        if (businessSize == 0) {
            viewDataBinding.tvEmptyMessageBusiness?.visibility = VISIBLE
        } else viewDataBinding.tvEmptyMessageBusiness?.visibility = GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEAL_REQUEST && resultCode == Activity.RESULT_OK) {
            val type = data?.getIntExtra(AppConstant.TYPE, -1)
            if (type == AppConstant.PERSONAL_CHAT) {
                viewModel.chatObserver.screenObserver = 0
            } else {
                viewModel.chatObserver.screenObserver = 1
            }
            openFragment(AppConstant.MY_DEAL)
        }
    }

}