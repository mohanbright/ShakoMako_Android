package com.io.app.shakomako.dagger.module

import com.io.app.shakomako.ui.analytics.FragmentAnalytics
import com.io.app.shakomako.ui.chat.ChatFragment
import com.io.app.shakomako.ui.deal.MyDealFragment
import com.io.app.shakomako.ui.delivery.DeliveryAddressFragment
import com.io.app.shakomako.ui.home.fragments.HomeFragment
import com.io.app.shakomako.ui.business.OtherBusinessProfile
import com.io.app.shakomako.ui.profile.ProfileFragment
import com.io.app.shakomako.ui.profile.edit.EditProfileFragment
import com.io.app.shakomako.ui.profile.verify.PersonalVerifyFragment
import com.io.app.shakomako.ui.profile_analytics.FragmentProfileAnalytics
import com.io.app.shakomako.ui.shop.ShopFragment
import com.io.app.shakomako.ui.shop.verify.VerifyBusinessFragment
import com.io.app.shakomako.ui.shopitemdetails.ShopItemDetailFragment
import com.io.app.shakomako.ui.trending.TrendingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment


    @ContributesAndroidInjector
    abstract fun contributeChatFragment(): ChatFragment

    @ContributesAndroidInjector
    abstract fun contributeTrendingFragment(): TrendingFragment

    @ContributesAndroidInjector
    abstract fun contributeVerifyBusinessFragment(): VerifyBusinessFragment

    @ContributesAndroidInjector
    abstract fun contributeMyDealFragment(): MyDealFragment

    @ContributesAndroidInjector
    abstract fun contributeVerifyPersonalFragment(): PersonalVerifyFragment

    @ContributesAndroidInjector
    abstract fun contributeFragmentAnalytics(): FragmentAnalytics

    @ContributesAndroidInjector
    abstract fun contributeProfileAnalyticsFragment(): FragmentProfileAnalytics

    @ContributesAndroidInjector
    abstract fun contributeShopFragment(): ShopFragment

    @ContributesAndroidInjector
    abstract fun contributeEditFragment(): EditProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeProductDetailFragment(): OtherBusinessProfile

    @ContributesAndroidInjector
    abstract fun contributeDeliveryAddressFragment(): DeliveryAddressFragment

    @ContributesAndroidInjector
    abstract fun contributeShopItemDetailFragment(): ShopItemDetailFragment

}