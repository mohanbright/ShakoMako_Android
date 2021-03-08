package com.io.app.shakomako.dagger.module

import android.app.Application
import android.content.Context
import com.io.app.shakomako.application.ShakoMakoApplication
import com.io.app.shakomako.dagger.component.ActivityComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [UiModule::class], subcomponents = [ActivityComponent::class])
public class BaseModule {

    @Provides
    @Singleton
    fun provideApplication(app: ShakoMakoApplication): Application = app

    @Provides
    @Singleton
    public fun provideContext(app: ShakoMakoApplication): Context = app.applicationContext


    /*@Provides
    @Singleton
    public Context provideContext(@NonNull FlipperApp flipperApp){
        return flipperApp.getApplicationContext();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference(FlipperApp flipperApp){
        return flipperApp.getSharedPreferences(flipperApp.getResources().getString(R.string.shared_pref_file_name), Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideSharedPreferencesEditor(SharedPreferences sharedPreferences){
        return sharedPreferences.edit();
    }

    @Provides
    @Singleton
    public UserSession provideUserSession(@NonNull SharedPreferences sharedPreferences, SharedPreferences.Editor editor){
        return new UserSession(sharedPreferences, editor);
    }*/
}