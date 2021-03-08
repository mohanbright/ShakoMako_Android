package com.io.app.shakomako.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.io.app.shakomako.dagger.factory.BaseViewModelFactory
import com.io.app.shakomako.ui.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class UiModule {

    @Binds
    abstract fun bindViewModelFactory(baseViewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

     @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): SplashActivity
}