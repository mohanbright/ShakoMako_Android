package com.io.app.shakomako.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivityIntroBinding
import com.io.app.shakomako.databinding.DialogTermsPolicyBinding
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.HomeActivity


class IntroActivity : DataBindingActivity<ActivityIntroBinding>(), ViewClickCallback {
    private var dialog: Dialog? = null


    override fun layoutRes(): Int = R.layout.activity_intro
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MainViewModel::class.java)
        dataBinding.apply {

        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_intro -> {
                showDialog()

            }
            R.id.tv_privacy -> {
                openWeb("https://www.shakomako.app/privacy-policy")
                dialog!!.dismiss()


            }
            R.id.tv_cancel -> {
                if (dialog != null) {
                    dialog!!.dismiss()
                    dialog = null
                }

            }
            R.id.tv_terms -> {
                openWeb("https://www.shakomako.app/terms-and-conditions")
                dialog!!.dismiss()


            }
            R.id.tv_agree -> {
                startNewActivity(HomeActivity::class.java)
                finishAffinity()
            }

        }
    }

    private fun showDialog() {
        dialog = Dialog(getThisActivity(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        val dialogTermsPolicyBinding = DataBindingUtil.inflate<DialogTermsPolicyBinding>(
            LayoutInflater.from(getThisActivity()), R.layout.dialog_terms_policy, null, false
        )
        dialog?.run {
            setContentView(dialogTermsPolicyBinding.root)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.setDimAmount(0.8f)
            setCancelable(false)
        }
        dialogTermsPolicyBinding.clickHandler = this
        dialog!!.show()

    }

    private fun openWeb(link: String) {
        val browser = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browser)
    }

}