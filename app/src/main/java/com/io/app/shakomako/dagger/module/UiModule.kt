package com.io.app.shakomako.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.io.app.shakomako.dagger.factory.BaseViewModelFactory
import com.io.app.shakomako.ui.address.AddressActivity
import com.io.app.shakomako.ui.chat.activity.ChatActivity
import com.io.app.shakomako.ui.filter.FilterActivity
import com.io.app.shakomako.ui.home.HomeActivity
import com.io.app.shakomako.ui.invoice.ChatInvoiceActivity
import com.io.app.shakomako.ui.like.LikesActivity
import com.io.app.shakomako.ui.login.InstagramWebActivity
import com.io.app.shakomako.ui.login.LoginActivity
import com.io.app.shakomako.ui.main.IntroActivity
import com.io.app.shakomako.ui.main.MainActivity
import com.io.app.shakomako.ui.map.MapActivity
import com.io.app.shakomako.ui.otp.OtpActivity
import com.io.app.shakomako.ui.product.AddProductActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ViewModelModule::class])
abstract class UiModule {

    @Binds
    abstract fun bindViewModelFactory(baseViewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector()
    abstract fun contributeSplashActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeOtpActivity(): OtpActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeAddProductActivity(): AddProductActivity

    @ContributesAndroidInjector
    abstract fun contributeAddressActivity(): AddressActivity

    @ContributesAndroidInjector
    abstract fun contributeChatInvoiceActivity(): ChatInvoiceActivity

    @ContributesAndroidInjector
    abstract fun contributeFilterActivity(): FilterActivity

    @ContributesAndroidInjector
    abstract fun contributeIntroActivity(): IntroActivity

    @ContributesAndroidInjector
    abstract fun contributeInstagramActivity(): InstagramWebActivity

    @ContributesAndroidInjector
    abstract fun contributeChatActivity(): ChatActivity

    @ContributesAndroidInjector
    abstract fun contributeMapActivity(): MapActivity

    @ContributesAndroidInjector
    abstract fun contributeLikeActivity(): LikesActivity
}