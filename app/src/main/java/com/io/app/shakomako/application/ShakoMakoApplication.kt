package com.io.app.shakomako.application

import android.app.Application
import com.io.app.shakomako.dagger.component.AppComponent
import com.io.app.shakomako.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ShakoMakoApplication : Application(), HasAndroidInjector {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
    }


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>



    companion object {
        lateinit var appInstance: ShakoMakoApplication
        lateinit var appComponent: AppComponent

        @Synchronized
        fun getInstance() : ShakoMakoApplication= appInstance

        @Synchronized
        fun getComponent(): AppComponent = appComponent
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}