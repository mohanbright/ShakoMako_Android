package com.io.app.shakomako.dagger.component

import com.io.app.shakomako.application.ShakoMakoApplication
import com.io.app.shakomako.dagger.module.BaseModule
import com.io.app.shakomako.dagger.module.RestModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Component(modules = [AndroidInjectionModule::class, BaseModule::class, RestModule::class])
@Singleton
interface AppComponent {
    fun inject(app: ShakoMakoApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: ShakoMakoApplication): Builder
        fun build(): AppComponent
    }
}
