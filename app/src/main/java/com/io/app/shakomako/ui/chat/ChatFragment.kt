package com.io.app.shakomako.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
                    startActivity(
                        Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                            AppConstant.PARCEL_DATA,
                            l
                        ).putExtra(AppConstant.TYPE, AppConstant.BUSINESS_CHAT)
                    )
                }
            })


        personalChatAdapter = PersonalChatAdapter(
            getBaseActivity(),
            object : RecyclerClickHandler<Int, PersonalChatResponse, Int> {
                override fun onClick(k: Int, l: PersonalChatResponse, m: Int) {
                    startActivity(
                        Intent(getBaseActivity(), ChatActivity::class.java).putExtra(
                            AppConstant.PARCEL_DATA,
                            l
                        ).putExtra(AppConstant.TYPE, AppConstant.PERSONAL_CHAT)
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
            Log.e("test", "${it.size}")
            businessAdapter.notifyDataSetChanged()

        })
    }


    private fun initPersonalObserver() {
        viewModel.getpersonalChatList(apiListener()).observe(viewLifecycleOwner, Observer {
            Log.e("test", "${it.size}")
            personalChatAdapter.personalChatList = it as ArrayList<PersonalChatResponse>
            personalChatAdapter.notifyDataSetChanged()

        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_personal -> {
                viewModel.chatObserver.screenObserver = 0
            }

            R.id.tv_business -> {
                viewModel.chatObserver.screenObserver = 1
            }
        }
    }

}