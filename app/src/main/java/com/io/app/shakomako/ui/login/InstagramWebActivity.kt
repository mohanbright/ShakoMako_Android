package com.io.app.shakomako.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.InstagramWebActivtiyBinding
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.main.MainActivity
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.AppConstant.Companion.AMPERSAND
import com.io.app.shakomako.utils.constants.AppConstant.Companion.AUTHORIZE_PARAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.CLIENT_ID
import com.io.app.shakomako.utils.constants.AppConstant.Companion.CLIENT_ID_PARAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.CLIENT_SECRET_ID
import com.io.app.shakomako.utils.constants.AppConstant.Companion.CODE
import com.io.app.shakomako.utils.constants.AppConstant.Companion.COMMA
import com.io.app.shakomako.utils.constants.AppConstant.Companion.EQUALS
import com.io.app.shakomako.utils.constants.AppConstant.Companion.INSTAGRAM_SOCIAL_TOKEN_KEY
import com.io.app.shakomako.utils.constants.AppConstant.Companion.INTENT_CODE_FOR_INSTAGRAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.OAUTH_PARAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.QUESTION_MARK
import com.io.app.shakomako.utils.constants.AppConstant.Companion.REDIRECT_URI
import com.io.app.shakomako.utils.constants.AppConstant.Companion.RESPONSE_TYPE_PARAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.SCOPE_PARAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.SLASH
import com.io.app.shakomako.utils.constants.AppConstant.Companion.TOKEN_URL
import com.io.app.shakomako.utils.constants.AppConstant.Companion.USER_MEDIA
import com.io.app.shakomako.utils.constants.AppConstant.Companion.USER_PROFILE
import com.io.app.shakomako.utils.constants.AppConstant.Companion.redirectURIURLEncoded
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class InstagramWebActivity : DataBindingActivity<InstagramWebActivtiyBinding>() {
    override fun layoutRes(): Int = R.layout.instagram_web_activtiy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadWebView()

    }


    private fun getAuthorizationUrl(): String {
        return (AppConstant.BASE_URL
                + OAUTH_PARAM + SLASH + AUTHORIZE_PARAM + SLASH + QUESTION_MARK + CLIENT_ID_PARAM + EQUALS + CLIENT_ID +
                AMPERSAND + REDIRECT_URI + EQUALS + redirectURIURLEncoded +
                AMPERSAND + SCOPE_PARAM + EQUALS + USER_PROFILE + COMMA + USER_MEDIA +
                AMPERSAND + RESPONSE_TYPE_PARAM + EQUALS + CODE)
//                return "https://api.instagram.com/oauth/authorize?client_id=233262804841972&redirect_uri=https://www.google.com/&response_type=code&scope=user_profile,user_media"
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        dataBinding.apply {
            web.settings.javaScriptEnabled = true   // Enable Javascript
            web.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }

                //insta_6069
//test@123
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String
                ): Boolean {
                    Log.e("redirect", url)
                    if (url.indexOf("code") == 61) {
                        getCode(Uri.parse(url))
                        return true
                    }
                    return false
                }


            }
            val authUrl: String = getAuthorizationUrl()
            Log.e("###", "Authorize: $authUrl")
            web.loadUrl(authUrl)

        }

    }

    /*uri.queryParameterNames
    uri.getQueryParameter("e")*/
    private fun getCode(uri: Uri) {
//"
        val code: String? =
            uri.toString().split("&")[0].split("code")[1].split("%3D")[1].split("%23_")[0]
        if (code != null) {
            Log.e("Success", "$code")
            getAccessToken(code)
        } else if (uri.getQueryParameter("error") != null) {
            val errorMsg: String = uri.getQueryParameter("error_description")!!
            Log.e("error", "message $errorMsg")
//        handleError(Throwable(errorMsg))
        }
    }


//


    private fun getAccessToken(code: String) {
        BaseUtils.showProgressbar(this)
        val formBody: RequestBody = FormBody.Builder()
            .add("client_id", CLIENT_ID)
            .add("client_secret", CLIENT_SECRET_ID)
            .add("grant_type", "authorization_code")
            .add("redirect_uri", redirectURIURLEncoded)
            .add("code", code)
            .build()
        val request: Request = Request.Builder().post(formBody)
            .url(TOKEN_URL)
            .build()
//        loadingDialog.show()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("valuee", "exception ${e.localizedMessage}")
                BaseUtils.hideProgressbar()

            }

            override fun onResponse(call: Call, response: Response) {
                BaseUtils.hideProgressbar()
                if (!response.isSuccessful) {
                    finish()
                } else {
                    val s = response.body?.string()
                    Log.e("TAG", "$s")
                    val jsonObj = JSONObject(s)
                    val value = jsonObj.getString("user_id") ?: ""
                    val intent = Intent()
                    intent.putExtra(INSTAGRAM_SOCIAL_TOKEN_KEY, value)
                    setResult(INTENT_CODE_FOR_INSTAGRAM, intent)
                    finish()
                }


            }

        })
    }

}

