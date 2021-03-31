package com.io.app.shakomako.ui.profile

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.profile.ProfileResponse
import com.io.app.shakomako.databinding.LayoutLogoutBinding
import com.io.app.shakomako.databinding.ProfileFragmentBinding
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.main.MainActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant

class ProfileFragment : HomeBaseFragment<ProfileFragmentBinding>(), ViewClickCallback {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        isHomeActivity(false)
    }

    override fun layoutRes(): Int {
        return R.layout.profile_fragment
    }

    private fun init() {
        viewDataBinding.viewHandler = this

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(getBaseActivity(), gso)

        viewModel.getUserProfile(apiListener)
            .observe(viewLifecycleOwner, Observer { response ->
                run {
                    if (response.status?.equals(ApiConstant.SUCCESS) == true) {
                        viewModel.profileObserver.profileObserverData =
                            response.body ?: ProfileResponse()
                        viewDataBinding.data = response.body
                        loadImage(viewModel.profileObserver.profileObserverData.userImage)

                    } else showToast(
                        response.message
                            ?: getBaseActivity().resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            })
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
                    viewDataBinding.progressBarMedium?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewDataBinding.progressBarMedium?.visibility = View.GONE
                    return false
                }
            }).into(viewDataBinding.profileImage)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_verify -> {
                openFragment(AppConstant.VERIFY_PROFILE_FRAGMENT)
            }

            R.id.ll_delivery_address -> {
                openFragment(AppConstant.DELIVERY_ADDRESS)
            }

            R.id.tv_edit -> {
                openFragment(AppConstant.EDIT_PROFILE)
            }

            R.id.ll_logout -> {
                showLogoutDialog()
            }
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener {
            Log.e("success", "true")

        }

    }

    var logoutDialog: Dialog? = null
    private fun showLogoutDialog() {
        if (logoutDialog == null && !getThisActivity().isFinishing) {
            val binding = DataBindingUtil.inflate<LayoutLogoutBinding>(
                LayoutInflater.from(getThisActivity()),
                R.layout.layout_logout,
                null,
                false
            )
            logoutDialog = Dialog(getThisActivity())
            logoutDialog!!.setContentView(binding.root)
            logoutDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            logoutDialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            logoutDialog!!.setCancelable(false)
            logoutDialog!!.show()
            binding.buttonLogout.setOnClickListener {
                BaseUtils.showProgressbar(getThisActivity())
                Handler(Looper.myLooper()!!).postDelayed({
                    BaseUtils.hideProgressbar()
                    logoutDialog!!.dismiss()
                    viewModel.userSession.logout()
                    signOut()
                    LoginManager.getInstance().logOut()
                    startNewActivity(MainActivity::class.java)
                    finishAffinity()
                }, 2000)

            }
            binding.cancel.setOnClickListener {
                logoutDialog!!.dismiss()
                logoutDialog = null
            }
        }
    }

    private var apiListener: ApiListener = object : ApiListener {
        override fun showProgress(isVisible: Boolean) {
            if (isVisible) BaseUtils.showProgressbar(getThisActivity())
            else BaseUtils.hideProgressbar()
        }

        override fun msg(msg: String) {
            showToast(msg)
        }
    }
}