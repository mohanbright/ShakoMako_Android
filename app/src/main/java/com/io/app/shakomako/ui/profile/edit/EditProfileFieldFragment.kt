package com.io.app.shakomako.ui.profile.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentEditProfileFieldBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.otp.OtpActivity
import com.io.app.shakomako.utils.ProfileFieldType
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant


class EditProfileFieldFragment : HomeBaseFragment<FragmentEditProfileFieldBinding>(),
    ViewClickCallback {

    var usernameString: String = ""

    override fun layoutRes(): Int {
        return R.layout.fragment_edit_profile_field
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    private fun init() {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.viewHandler = this

        setHeading()
    }

    private fun setHeading() {
        when (viewModel.editProfileFieldObserver.type) {
            ProfileFieldType.SHAKOMAKO_ID -> {
                viewDataBinding.header = "Edit ShakoMako ID:"
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.shakoMakoUserName
                usernameString = viewModel.profileObserver.profileObserverData.shakoMakoUserName
                setListener()
            }
            ProfileFieldType.FULL_NAME -> {
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.userName
                viewDataBinding.header = "Edit Full Name:"
            }
            ProfileFieldType.EMAIL -> {
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.userEmail ?: ""
                viewDataBinding.header = "Edit Email:"
            }
            ProfileFieldType.PHONE -> {
                viewDataBinding.ccp.visibility = VISIBLE
                viewDataBinding.etField.inputType = InputType.TYPE_CLASS_NUMBER
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.userPhone
                viewDataBinding.header = "Edit Phone:"
            }
            ProfileFieldType.GENDER -> {
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.userGender
                viewDataBinding.header = "Edit Gender:"
            }
            ProfileFieldType.DOB -> {
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.dateOfBirth
                viewDataBinding.header = "Edit Date Of Birth:"
            }
        }
    }

    private fun setListener() {
        viewDataBinding.root.setOnClickListener {
            viewDataBinding.etField.clearFocus()
        }

        viewDataBinding.etField.setOnFocusChangeListener { _, p1 ->
            run {
                if (!p1) {
                    checkUserName(updating = false)
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_save -> {
                handleSaveClick()
            }

            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    private fun handleSaveClick() {
        if (viewModel.editProfileFieldObserver.editedText.trim().isEmpty()) return

        when (viewModel.editProfileFieldObserver.type) {
            ProfileFieldType.SHAKOMAKO_ID -> {
                viewModel.profileObserver.profileObserverData.shakoMakoUserName =
                    usernameString
                checkUserName(updating = true)
            }
            ProfileFieldType.FULL_NAME -> {
                viewModel.profileObserver.profileObserverData.userName =
                    viewModel.editProfileFieldObserver.editedText
                updateProfile()

            }
            ProfileFieldType.EMAIL -> {
                viewModel.profileObserver.profileObserverData.userEmail =
                    viewModel.editProfileFieldObserver.editedText
                updateProfile()

            }
            ProfileFieldType.PHONE -> {
                viewModel.profileObserver.profileObserverData.userPhone =
                    viewModel.editProfileFieldObserver.editedText
                updateUserEmailPhone("+" + viewDataBinding.ccp.selectedCountryCode + viewModel.profileObserver.profileObserverData.userPhone)

            }
            ProfileFieldType.GENDER -> {
                updateProfile()

            }
            ProfileFieldType.DOB -> {
                updateProfile()

            }
        }
    }

    private fun updateProfile() {
        viewModel.updateUserProfile(
            viewModel.profileObserver.profileObserverData,
            apiListener()
        )
            .observe(viewLifecycleOwner,
                Observer {
                    Log.e("response", "${it.message}")
                    onBackPressed()
                })
    }

    private fun checkUserName(updating: Boolean) {
        if (usernameString == viewModel.editProfileFieldObserver.editedText || viewModel.editProfileFieldObserver.editedText.trim()
                .isEmpty()
        ) if (updating) {
            onBackPressed()
            return
        } else return

        viewModel.createUserName(viewModel.editProfileFieldObserver.editedText)
            .observe(viewLifecycleOwner,
                Observer {
                    Log.e("status", "${it.status}")
                    if (it.status == 200) {
                        viewModel.visibleObserver.visible = true
                        usernameString = viewModel.editProfileFieldObserver.editedText
                        if (updating) updateProfile()
                    } else {
                        viewModel.visibleObserver.visible = false
                        viewDataBinding.etField.error = it.message


                    }
                })
    }

    private fun updateUserEmailPhone(phoneNumber: String) {
        viewModel.updateUserEmailPhone(apiListener(), AppConstant.LOGIN_TYPE_PHONE, phoneNumber)
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        startActivity(
                            Intent(getBaseActivity(), OtpActivity::class.java).putExtra(
                                AppConstant.TYPE,
                                AppConstant.CHANGE_PHONE_NUMBER
                            ).putExtra(
                                AppConstant.PARCEL_DATA,
                                "+" + viewDataBinding.ccp.selectedCountryCode + viewModel.profileObserver.profileObserverData.userPhone
                            )
                        )
                    } else showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
    }

}
