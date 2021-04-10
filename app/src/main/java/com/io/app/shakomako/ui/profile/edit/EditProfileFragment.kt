package com.io.app.shakomako.ui.profile.edit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.FragmentEditProfileBinding
import com.io.app.shakomako.databinding.LayoutChatOptionsBinding
import com.io.app.shakomako.databinding.LayoutGenderSelectionBinding
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.utils.ContextUtils
import com.io.app.shakomako.utils.ProfileFieldType
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import java.util.*

class EditProfileFragment : HomeBaseFragment<FragmentEditProfileBinding>(), ViewClickCallback {

    var usernameString: String = ""

    override fun layoutRes(): Int {
        return R.layout.fragment_edit_profile
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewDataBinding.viewHandler = this

        viewDataBinding.data = viewModel.profileObserver.profileObserverData
        loadImage(viewModel.profileObserver.profileObserverData.userImage)

//        viewDataBinding.root.setOnClickListener {
//            viewDataBinding.etUsername.clearFocus()
//        }
//
//        viewDataBinding.etUsername.setOnFocusChangeListener { _, p1 ->
//            run {
//                if (p1) {
//                    Log.e("text", "dumy true")
//
//                } else {
//                    viewModel.createUserName(viewModel.profileObserver.profileObserverData.shakoMakoUserName)
//                        .observe(viewLifecycleOwner,
//                            Observer {
//                                Log.e("status", "${it.status}")
//                                if (it.status == 200) {
//                                    viewModel.visibleObserver.visible = true
//
//                                    usernameString =
//                                        viewModel.profileObserver.profileObserverData.shakoMakoUserName
//                                } else {
//                                    viewModel.visibleObserver.visible = false
//                                    viewDataBinding.etUsername.error = it.message
//
//
//                                }
//                            })
//                    Log.e("text", "dumy false")
//                }
//            }
//        }
    }

    @SuppressLint("CheckResult")
    private fun loadImage(url: String) {
        Glide.with(viewDataBinding.root).load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewDataBinding.progressBarMedium.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewDataBinding.progressBarMedium.visibility = View.GONE
                    return false
                }
            }).into(viewDataBinding.profileImage)
    }

    private fun updateProfile() {
        viewModel.updateUserProfile(
            viewModel.profileObserver.profileObserverData,
            apiListener()
        )
            .observe(viewLifecycleOwner,
                Observer {
                    Log.e("response", "${it.message}")

                })
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.tv_shakomako_label -> {
                viewModel.editProfileFieldObserver.type = ProfileFieldType.SHAKOMAKO_ID
                openFragment(AppConstant.EDIT_PROFILE_FIELD)
            }

            R.id.tv_full_name -> {
                viewModel.editProfileFieldObserver.type = ProfileFieldType.FULL_NAME
                openFragment(AppConstant.EDIT_PROFILE_FIELD)
            }

            R.id.tv_email -> {
                if (viewModel.profileObserver.profileObserverData.loginType == AppConstant.LOGIN_TYPE_GOOGLE || viewModel.profileObserver.profileObserverData.loginType == AppConstant.LOGIN_TYPE_FACEBOOK) {
                    showToast("You cannot edit your email")
                    return
                }
                viewModel.editProfileFieldObserver.type = ProfileFieldType.EMAIL
                openFragment(AppConstant.EDIT_PROFILE_FIELD)
            }

            R.id.tv_phone -> {
                if (viewModel.profileObserver.profileObserverData.loginType == AppConstant.LOGIN_TYPE_PHONE) {
                    showToast("You cannot edit your phone")
                    return
                }
                viewModel.editProfileFieldObserver.type = ProfileFieldType.PHONE
                openFragment(AppConstant.EDIT_PROFILE_FIELD)
            }

            R.id.ll_verify -> {
                openFragment(AppConstant.VERIFY_PROFILE_FRAGMENT)
            }

            R.id.iv_back -> {
                onBackPressed()
            }

            R.id.tv_date_of_birth -> {
                showDobDialog()
            }

            R.id.tv_gender -> showBottomSheet()

            R.id.text_submit -> {
                viewModel.profileObserver.profileObserverData.shakoMakoUserName = usernameString
                if (viewModel.profileObserver.profileObserverData.userImage == ""
                    || viewModel.profileObserver.profileObserverData.userImage.contains("http://ec2-3-142-205-39.us-east-2.compute.amazonaws.com:3000/")
                )
                    updateProfile()
                else {
                    viewModel.upload(
                        apiListener(),
                        viewModel.profileObserver.profileObserverData.userImage
                    )
                        .observe(viewLifecycleOwner,
                            Observer { response ->
                                run {
                                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                                        viewModel.profileObserver.profileObserverData.userImage =
                                            response.body?.image ?: ""
                                        updateProfile()
                                    } else showToast(
                                        response.message
                                            ?: resources.getString(R.string.msg_something_went_wrong)
                                    )
                                }
                            })
                }

            }

            R.id.edit_profile_image -> {
                openSingleImagePicker(object : DataItemCallBack<Uri, Int> {
                    override fun onItemData(t: Uri?, r: Int?) {
                        if (r == 0)
                            Glide.with(viewDataBinding.root).load(t?.path.toString())
                                .into(viewDataBinding.profileImage)
                        viewModel.profileObserver.profileObserverData.userImage = t?.path.toString()

                    }
                })
            }
        }
    }

    private fun showDobDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val datePickerDialog = DatePickerDialog(getBaseActivity(), R.style.AlertStyle)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.setOnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val date: String
                val month: Int = monthOfYear + 1
                date =
                    if (ContextUtils.getDigits(month) < 2 && ContextUtils.getDigits(dayOfMonth) < 2) {
                        "$year-0$month-0$dayOfMonth"
                    } else if (ContextUtils.getDigits(month) < 2) {
                        "$year-0$month-$dayOfMonth"
                    } else if (ContextUtils.getDigits(dayOfMonth) < 2) {
                        "$year-$month-0$dayOfMonth"
                    } else {
                        "$year-$month-$dayOfMonth"
                    }
                viewModel.profileObserver.profileObserverData.dateOfBirth = date
            }
            datePickerDialog.show()
        }
    }

    private fun showBottomSheet() {
        val dialogFragment =
            BottomSheetDialog(getBaseActivity(), R.style.Widget_ShakoMako_BottomSheetStyle)
        val bottomSheetBinding: LayoutGenderSelectionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(getBaseActivity()),
            R.layout.layout_gender_selection,
            null,
            false
        )
        bottomSheetBinding.viewHandler = object : ViewClickCallback {
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.tv_male -> viewModel.profileObserver.profileObserverData.userGender =
                        resources.getString(R.string.male)
                    R.id.tv_female -> viewModel.profileObserver.profileObserverData.userGender =
                        resources.getString(R.string.female)
                    R.id.tv_other -> viewModel.profileObserver.profileObserverData.userGender =
                        resources.getString(R.string.other)
                }
                dialogFragment.dismiss()
            }
        }
        dialogFragment.setContentView(bottomSheetBinding.root)
        dialogFragment.show()
    }
}