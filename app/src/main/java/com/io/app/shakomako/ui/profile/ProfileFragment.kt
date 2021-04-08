package com.io.app.shakomako.ui.profile

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import com.io.app.shakomako.databinding.LayoutAlertDialogBinding
import com.io.app.shakomako.databinding.LayoutLanguageBinding
import com.io.app.shakomako.databinding.LayoutLogoutBinding
import com.io.app.shakomako.databinding.ProfileFragmentBinding
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseFragment
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.HomeBaseFragment
import com.io.app.shakomako.ui.main.MainActivity
import com.io.app.shakomako.ui.notification.NotificationActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.constants.AppConstant
import kotlin.system.exitProcess

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

            R.id.ll_notification -> startActivity(
                Intent(
                    getBaseActivity(),
                    NotificationActivity::class.java
                )
            )

            R.id.ll_logout -> {
                showLogoutDialog()
            }

            R.id.ll_lang -> {
                showLangDialog()
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

    var alertDialog: Dialog? = null
    private fun showalertDailog(type: String) {
        if (alertDialog == null && !getThisActivity().isFinishing) {
            val binding = DataBindingUtil.inflate<LayoutAlertDialogBinding>(
                LayoutInflater.from(getThisActivity()),
                R.layout.layout_alert_dialog,
                null,
                false
            )
            alertDialog = Dialog(getThisActivity())
            alertDialog!!.setContentView(binding.root)
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            alertDialog!!.window?.setDimAmount(.8f)
            alertDialog!!.setCancelable(false)
            alertDialog!!.show()
            binding.subtitle = resources.getString(R.string.alert_subtitle)
            binding.viewHandler = object : ViewClickCallback {
                override fun onClick(v: View) {
                    when (v.id) {
                        R.id.tv_cancel -> {
                            alertDialog!!.dismiss()
                            alertDialog = null

                        }

                        R.id.tv_quit -> {
                            viewModel.userSession.saveLanguage(type)
                            alertDialog!!.dismiss()
                            langDialog?.dismiss()
                            BaseUtils.showProgressbar(getThisActivity())
                            Handler(Looper.myLooper()!!).postDelayed(
                                {

                                    BaseUtils.hideProgressbar()
                                    relaunch()
                                }, 1000
                            )


                        }
                    }
                }

            }
        }
    }

    fun relaunch() {
        val mStartActivity = Intent(context, MainActivity::class.java)
        val mPendingIntentId = 123456
        val mPendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            mPendingIntentId,
            mStartActivity,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val mgr: AlarmManager =
            getThisActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        exitProcess(0)
    }

    var langDialog: Dialog? = null
    private fun showLangDialog() {
//if (logoutDialog == null && !getThisActivity().isFinishing) {
        val binding = DataBindingUtil.inflate<LayoutLanguageBinding>(
            LayoutInflater.from(getThisActivity()),
            R.layout.layout_language,
            null,
            false
        )
        viewModel.languageObserver.langObserver =
            if (viewModel.userSession.language.equals(AppConstant.LANGUAGE_TYPE_ARABIC))
                "ar"
            else
                "en"

        langDialog = Dialog(getThisActivity())
        langDialog!!.setContentView(binding.root)
        langDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        langDialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        langDialog!!.window?.setDimAmount(.8f)
// langDialog!!.setCancelable(false)
        langDialog!!.show()

        binding.observer = viewModel.languageObserver

        binding.viewCallback = object : ViewClickCallback {
            override fun onClick(v: View) {
                when (v.id) {
                    R.id.iv_radio_ar -> {
                        viewModel.languageObserver.langObserver = "ar"
                        showalertDailog(AppConstant.LANGUAGE_TYPE_ARABIC)
                    }
                    R.id.iv_radio_en -> {
                        viewModel.languageObserver.langObserver = "en"
                        showalertDailog(AppConstant.LANGUAGE_TYPE_ENGLISH)
                    }

                }
            }
        }
    }
}