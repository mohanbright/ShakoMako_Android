package com.io.app.shakomako.dagger.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.io.app.shakomako.R
import com.io.app.shakomako.application.ShakoMakoApplication
import com.io.app.shakomako.dagger.component.ActivityComponent
import com.io.app.shakomako.ui.base.BaseUtils
import com.io.app.shakomako.utils.session.UserSession
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [UiModule::class], subcomponents = [ActivityComponent::class])
class BaseModule {

    @Provides
    @Singleton
    fun provideApplication(app: ShakoMakoApplication): Application = app

    @Provides
    @Singleton
    fun provideContext(app: ShakoMakoApplication): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreference(shakoMakoApplication: ShakoMakoApplication): SharedPreferences {
        return shakoMakoApplication.getSharedPreferences(shakoMakoApplication.resources.getString(R.string.shared_pref_file),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesEditor(sharedPreference: SharedPreferences): SharedPreferences.Editor {
        return sharedPreference.edit()
    }

    @Provides
    @Singleton
    fun provideUserSession(
        sharedPreference: SharedPreferences,
        editor: SharedPreferences.Editor
    ): UserSession {
        return UserSession(sharedPreference, editor)
    }
}