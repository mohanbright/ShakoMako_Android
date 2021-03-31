package com.io.app.shakomako.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.io.app.shakomako.R
import com.io.app.shakomako.api.pojo.login.TokenResponse
import com.io.app.shakomako.api.pojo.response.ApiResponse
import com.io.app.shakomako.databinding.ActivityMainBinding
import com.io.app.shakomako.helper.callback.ApiListener
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.BaseActivity
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.ui.home.HomeActivity
import com.io.app.shakomako.ui.login.InstagramWebActivity
import com.io.app.shakomako.ui.login.LoginActivity
import com.io.app.shakomako.utils.constants.ApiConstant
import com.io.app.shakomako.utils.ApiUtils
import com.io.app.shakomako.utils.constants.AppConstant
import com.io.app.shakomako.utils.constants.AppConstant.Companion.INTENT_CODE_FOR_INSTAGRAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.LOGIN_TYPE_INSTAGRAM
import com.io.app.shakomako.utils.constants.AppConstant.Companion.RC_SIGN_IN
import com.io.app.shakomako.utils.session.SessionConstants
import org.json.JSONObject
import java.util.*

class MainActivity : DataBindingActivity<ActivityMainBinding>(), ViewClickCallback {


    override fun layoutRes(): Int = R.layout.activity_main
    private var facebookCallbackManager: CallbackManager? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
    private var TAG: String = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0,0)
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(MainViewModel::class.java)

        if (viewModel.userSession.getBooleanValue(SessionConstants.USER_LOGGED_IN)) {
            Log.e("TOKEN", viewModel.userSession.userToken ?: "")
            startNewActivity(HomeActivity::class.java)
        }


        initMainActivity()
    }

    fun openWeb() {
        startActivityForResult(
            Intent(getThisActivity(), InstagramWebActivity::class.java),
            INTENT_CODE_FOR_INSTAGRAM
        )
    }

    private fun initMainActivity() {
        dataBinding.viewHandler = this
        facebookCallbackManager = CallbackManager.Factory.create()
        loginWithGmail()
    }

    private fun fbLogin() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        if (!isLoggedIn) {
            LoginManager.getInstance().logOut()
        }
        if (ApiUtils.checkInternet(this)) {
            LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("email", "public_profile", "user_friends")
            )
            LoginManager.getInstance()
                .registerCallback(facebookCallbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        //hideProgress()
                        Log.e("facebook", loginResult.accessToken.token)
                        getFacebookData(loginResult)
                        // Toast.makeText(activity, ""+userStringEmail, Toast.LENGTH_SHORT).show();oo

                    }

                    override fun onCancel() {
                        //hideProgress()
                        Log.e("facebook", "cancel")
                    }

                    override fun onError(error: FacebookException) {
                        //hideProgress()
                        Log.e("facebook", error.message!!)
                        showToast(
                            getString(R.string.msg_something_went_wrong)
                        )
                        if (error is FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut()
                            }
                        }
                    }
                })
        } else {
            Toast.makeText(this, "Network Issue", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFacebookData(loginResult: LoginResult) {
        //showProgress()
        val graphRequest = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { `object`, response ->
            Log.e("facebook", "completed")
            //hideProgress()
            getDetailsFromFacebook(`object`)


        }
        val bundle = Bundle()
        Log.e("LoginActivity", "bundle set")
        bundle.putString("fields", "id, first_name, last_name,email,picture,gender,location")
        graphRequest.parameters = bundle
        graphRequest.executeAsync()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)

            }
            INTENT_CODE_FOR_INSTAGRAM -> {
                if (data != null) {
                    Log.e(TAG, "${data.getStringExtra(AppConstant.INSTAGRAM_SOCIAL_TOKEN_KEY)}")
                    viewModel.loginRequest.socialKey =
                        "${data.getStringExtra(AppConstant.INSTAGRAM_SOCIAL_TOKEN_KEY)}"
                    viewModel.loginRequest.loginType = LOGIN_TYPE_INSTAGRAM
                    login()
                }

            }
        }

    }

    private fun getDetailsFromFacebook(`object`: JSONObject?) {
        Log.e("TAG", "${`object`?.optString("id")}")
        if (`object` != null) {
            viewModel.loginRequest.socialKey = (`object`.optString("id"))
            viewModel.loginRequest.userName =
                (`object`.optString("first_name") + " " + `object`.optString("last_name"))
            viewModel.loginRequest.userEmail = (`object`.optString("email"))
            viewModel.loginRequest.userImage =
                (`object`.getJSONObject("picture").getJSONObject("data").getString("url"))
            viewModel.loginRequest.loginType = "facebook"
            login()
        } else {
            showToast(resources.getString(R.string.msg_something_went_wrong))
        }
    }

    /**
     * Gmail Authentication
     */
    private fun loginWithGmail() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            val acct = GoogleSignIn.getLastSignedInAccount(this@MainActivity)
            if (acct != null) {
                viewModel.loginRequest.socialKey = acct.id.toString()
                viewModel.loginRequest.userName = acct.displayName.toString()
                viewModel.loginRequest.userEmail = "${acct.email}"
                viewModel.loginRequest.userImage =
                    "${acct.photoUrl}"
                viewModel.loginRequest.loginType = "google"
                login()
            }
            if (account != null) {
                Log.d(
                    TAG,
                    "signInResult:success code=" + account.serverAuthCode
                )
            }
        } catch (e: ApiException) {
            Log.d(
                TAG,
                "signInResult:failed code=" + e.statusCode
            )
        }
    }

    private fun login() {
        viewModel.login(apiListener, viewModel.loginRequest)
            .observe(this, loginObserver)
    }

    private var loginObserver: androidx.lifecycle.Observer<ApiResponse<TokenResponse>> =
        androidx.lifecycle.Observer { response ->

            when (response.status) {
                ApiConstant.NEW_USER, ApiConstant.NEW_USER_, ApiConstant.SUCCESS -> {
                    viewModel.userSession.createSession(
                        response.body?.token!!, true
                    )
                    startNewActivity(IntroActivity::class.java)
                    finishAffinity()
                }

                ApiConstant.ALREADY_REGISTERED -> {
                    viewModel.userSession.createSession(
                        response.body?.token!!, true
                    )
                    startNewActivity(HomeActivity::class.java)
                    finishAffinity()
                }

                else -> {
                    showToast(
                        response.message ?: resources.getString(R.string.msg_something_went_wrong)
                    )
                }
            }
        }


    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(
                this
            ) {
                Log.e("success", "true")
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

    override fun getThisActivity(): BaseActivity = this@MainActivity

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_facebook -> {
                fbLogin()
            }

            R.id.tv_number -> {

                startActivity(Intent(this, LoginActivity::class.java))
            }

            R.id.tv_google -> {
                signIn()
            }

            R.id.tv_instagram -> {
                openWeb()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        val s = intent?.getStringExtra("TOKEN")
        Log.e("MainActivity", "$s")

        super.onNewIntent(intent)
    }

}