package com.io.app.shakomako.dagger.module

import androidx.lifecycle.ViewModel
import com.io.app.shakomako.dagger.factory.BaseViewModelKey
import com.io.app.shakomako.ui.address.AddressViewModel
import com.io.app.shakomako.ui.chat.activity.ChatViewModel
import com.io.app.shakomako.ui.home.HomeViewModel
import com.io.app.shakomako.ui.invoice.ChatInvoiceViewModel
import com.io.app.shakomako.ui.like.LikeViewModel
import com.io.app.shakomako.ui.login.LoginViewModel
import com.io.app.shakomako.ui.main.MainViewModel
import com.io.app.shakomako.ui.map.MapViewModel
import com.io.app.shakomako.ui.notification.NotificationViewModel
import com.io.app.shakomako.ui.product.AddProductViewModel
import com.io.app.shakomako.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @BaseViewModelKey(MainViewModel::class)
    abstract fun provideSplashViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(LoginViewModel::class)
    abstract fun provideLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(AddProductViewModel::class)
    abstract fun provideAddProductViewModel(viewModel: AddProductViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(AddressViewModel::class)
    abstract fun provideAddressViewModel(viewModel: AddressViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(ChatViewModel::class)
    abstract fun provideChatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(MapViewModel::class)
    abstract fun provideMapViewModel(viewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(ChatInvoiceViewModel::class)
    abstract fun provideChatInvoiceViewModel(viewModel: ChatInvoiceViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(LikeViewModel::class)
    abstract fun provideLikeViewModel(viewModel: LikeViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(NotificationViewModel::class)
    abstract fun provideNotificationViewModel(viewModel: NotificationViewModel): ViewModel

    @Binds
    @IntoMap
    @BaseViewModelKey(SearchViewModel::class)
    abstract fun provideSearchViewModel(viewModel: SearchViewModel): ViewModel


}