package com.io.app.shakomako.application

import android.app.Application
import android.util.Log
import com.facebook.FacebookSdk
import com.io.app.shakomako.dagger.component.AppComponent
import com.io.app.shakomako.dagger.component.DaggerAppComponent
import com.io.app.shakomako.utils.UnsafeHttpClient
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.inject.Inject
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession

class ShakoMakoApplication : Application(), HasAndroidInjector {

    var op: IO.Options? = null
    lateinit var sslContext: SSLContext
    val SOCKETS_URL = "http://ec2-3-142-205-39.us-east-2.compute.amazonaws.com:5000/"

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)

        FacebookSdk.sdkInitialize(this)


        op = IO.Options()

        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(
                null,
                arrayOf(
                    UnsafeHttpClient.getUnsafeOkHttpClient().x509TrustManager!!
                ),
                null
            )
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

    }


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    companion object {
        lateinit var appInstance: ShakoMakoApplication
        lateinit var appComponent: AppComponent

        @Synchronized
        fun getInstance(): ShakoMakoApplication = appInstance

        @Synchronized
        fun getComponent(): AppComponent = appComponent
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    fun getSocket(): Socket {
        return initSocket()
    }

    private fun initSocket(): Socket {
        var socket: Socket? = null
        try {
            socket = IO.socket(SOCKETS_URL)
        } catch (e: URISyntaxException) {
            Log.e("TAG", e.localizedMessage)
            e.printStackTrace()
        }
        return socket!!
    }
}