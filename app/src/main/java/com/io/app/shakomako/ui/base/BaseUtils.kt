package com.io.app.shakomako.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ProgressBarBinding
import javax.inject.Inject

object BaseUtils {



    var pDialog: Dialog? = null

    fun showProgressbar(context: Context) {
        if (pDialog == null) {
            pDialog= Dialog(context)
            val binding = DataBindingUtil.inflate<ProgressBarBinding>(
                LayoutInflater.from(context),
                R.layout.progress_bar,
                null,
                false
            )
            pDialog = context.let { Dialog(it) }
            pDialog!!.setContentView(binding.root)
            pDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            pDialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            pDialog!!.window?.setDimAmount(0.8f)
            pDialog!!.setCancelable(false)
            pDialog!!.show()


        }


    }

    fun hideProgressbar() {
        if (pDialog != null) {
            pDialog!!.apply {
                dismiss()

            }
            pDialog = null

        }

    }

    fun showMessage(context:Context,message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()


    }
}