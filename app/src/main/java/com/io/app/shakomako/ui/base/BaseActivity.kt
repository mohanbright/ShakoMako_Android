package com.io.app.shakomako.ui.base

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.io.app.shakomako.R
import com.io.app.shakomako.dagger.factory.BaseViewModelFactory
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.utils.ContextUtils
import com.io.app.shakomako.utils.FileUtils
import com.io.app.shakomako.utils.constants.AppConstant
import com.tedpark.tedpermission.rx2.TedRx2Permission
import dagger.android.support.DaggerAppCompatActivity
import gun0912.tedbottompicker.TedRxBottomPicker
import gun0912.tedimagepicker.builder.TedRxImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import id.zelory.compressor.Compressor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), BaseHandler {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory


    open fun <T : ViewModel?> getViewModel(viewModel: Class<T>?): T {
        return ViewModelProvider(this, viewModelFactory)[viewModel!!]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getFacebookHashKey()

    }

    private fun getFacebookHashKey() {
        val info: PackageInfo
        try {
            info =
                packageManager.getPackageInfo("com.io.app.shakomako", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
//String something = new Strixng(Base64.encodeBytes(md.digest()));
                Log.e("facebook ", "hash key $something")
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("facebook", "name not found$e1")
        } catch (e: NoSuchAlgorithmException) {
            Log.e("facebook", "no such an algorithm $e")
        } catch (e: Exception) {
            Log.e("facebook", "exception $e")
        }
    }

    override fun getThisActivity(): BaseActivity = this@BaseActivity

    override fun <T : Activity> startNewActivity(java: Class<T>) {
        val intent = Intent(this, java)
        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun attachBaseContext(newBase: Context?) {
        var locale = Locale("en")
        if (newBase != null) {
            ContextUtils.updateLocale(newBase, locale)
        };
        super.attachBaseContext(newBase)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun finishAffinity() {
        super.finishAffinity()
    }

    override fun apiListener(): ApiListener {
        return apiListener
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

    @SuppressLint("CheckResult")
    override fun checkThePermission(
        title: String,
        deniedMsg: String,
        dataItemCallback: DataItemCallBack<Int, Int>,
        vararg permissions: String
    ) {
        TedRx2Permission.with(this@BaseActivity)
            .setRationaleTitle(title)
            .setRationaleMessage(deniedMsg)
            .setPermissions(*permissions)
            .setGotoSettingButton(true)
            .setGotoSettingButtonText(resources.getString(R.string.text_open_setting))
            .request()
            .subscribe({ tedPermissionResult ->
                if (tedPermissionResult.isGranted) {
                    //todo
                    dataItemCallback.onItemData(AppConstant.PERMISSION_ACCEPTED, -1)
                } else {
                    dataItemCallback.onItemData(AppConstant.PERMISSION_DENIED, -1)
                }
            }, { throwable ->
                showToast(throwable.localizedMessage)
                dataItemCallback.onItemData(AppConstant.PERMISSION_DENIED, -1)
            })
    }

    override fun openSingleImagePicker(dataItemCallback: DataItemCallBack<Uri, Int>) {
        if (!isFinishing) {
            checkThePermission(
                resources.getString(R.string.text_accept_permission),
                resources.getString(R.string.msg_please_allow_us_to_use_permission),
                object : DataItemCallBack<Int, Int> {
                    @SuppressLint("CheckResult")
                    override fun onItemData(t: Int?, r: Int?) {
                        if (t == AppConstant.PERMISSION_ACCEPTED) {
                            TedRxImagePicker
                                .with(this@BaseActivity)
                                .mediaType(MediaType.IMAGE)
                                .cameraTileImage(R.drawable.ic_camera)
                                .cameraTileBackground(R.color.colorPrimary)
                                .showCameraTile(true)
                                .showTitle(true)
                                .title(resources.getString(R.string.text_select_image))
                                .backButton(R.drawable.ic_back)
                                .start()
                                .subscribe(
                                    { uri ->
                                        run {
                                            compressImage(
                                                FileUtils.from(getThisActivity(), uri),
                                                dataItemCallback
                                            )
                                        }
                                    },
                                    { throwable ->
                                        showToast(throwable.localizedMessage)
                                        dataItemCallback.onItemData(null, -1)
                                    })
                        } else {
                            showToast(resources.getString(R.string.msg_image_permission_not_granted))
                            dataItemCallback.onItemData(null, -1)
                        }
                    }


                },
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            dataItemCallback.onItemData(null, -1)
        }
    }

    @SuppressLint("CheckResult")
    fun compressImage(actualImage: File?, dataItemCallback: DataItemCallBack<Uri, Int>) {
        Compressor(this)
            .compressToFileAsFlowable(actualImage).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ file: File? ->
                Log.e("BaseActivity", "$file")
                dataItemCallback.onItemData(Uri.fromFile(file), 0)

            }
            ) { throwable: Throwable ->
                throwable.printStackTrace()

            }
    }

    override fun openMultipleImagePicker(dataItemCallback: DataItemCallBack<List<Uri>, Int>) {
        if (isFinishing) {
            checkThePermission(
                resources.getString(R.string.text_accept_permission),
                resources.getString(R.string.msg_please_allow_us_to_use_permission),
                object : DataItemCallBack<Int, Int> {
                    @SuppressLint("CheckResult")
                    override fun onItemData(t: Int?, r: Int?) {
                        if (t == AppConstant.PERMISSION_ACCEPTED) {
                            TedRxBottomPicker.with(this@BaseActivity as FragmentActivity)
                                .setPeekHeight(1600)
                                .showTitle(false)
                                .setSelectMaxCount(4)
                                .setCompleteButtonText("Done")
                                .setEmptySelectionText("No Select")
                                .setSelectMaxCountErrorText(resources.getString(R.string.msg_max_images_limit))
                                .showMultiImage()
                                .subscribe(
                                    { uris ->
                                        dataItemCallback.onItemData(
                                            uris as List<Uri>?,
                                            1
                                        )
                                    },
                                    { throwable ->
                                        showToast(throwable.localizedMessage)
                                        dataItemCallback.onItemData(null, -1)
                                    })
                        } else {
                            showToast(resources.getString(R.string.msg_image_permission_not_granted))
                            dataItemCallback.onItemData(null, -1)
                        }
                    }
                },
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        } else {
            dataItemCallback.onItemData(null, -1)
        }
    }

    override fun hideKeyboard() {
        try {
            val inputManager =
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            inputManager.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (ignored: java.lang.Exception) {
        }
    }
}