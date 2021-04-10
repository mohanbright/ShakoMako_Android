package com.io.app.shakomako.ui.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentEditProfileFieldBinding
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.ProfileFieldType
import com.io.app.shakomako.utils.constants.AppConstant

class EditProfileFieldFragment : HomeBaseFragment<FragmentEditProfileFieldBinding>() {

    override fun layoutRes(): Int {
        return R.layout.fragment_edit_profile_field
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
    }

    private fun init() {
        viewDataBinding.viewModel = viewModel
        setHeading()
    }

    private fun setHeading() {
        when (viewModel.editProfileFieldObserver.type) {
            ProfileFieldType.SHAKOMAKO_ID -> {
                viewDataBinding.header = "Edit ShakoMako ID:"
                viewModel.editProfileFieldObserver.editedText =
                    viewModel.profileObserver.profileObserverData.shakoMakoUserName
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
}
