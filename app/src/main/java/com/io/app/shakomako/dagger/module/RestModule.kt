package com.io.app.shakomako.dagger.module

import android.util.Log
import com.google.gson.GsonBuilder
import com.io.app.shakomako.R
import com.io.app.shakomako.api.repo.ApiRepository
import com.io.app.shakomako.application.ShakoMakoApplication
import com.io.app.shakomako.utils.session.UserSession
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RestModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e("OkHttp", message)
            }
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
    }

    @Provides
    @Singleton
    fun provideGsonBuilder(): GsonBuilder {
        //gson
        return GsonBuilder()
            .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        clientBuilder: OkHttpClient.Builder,
        gsonBuilder: GsonBuilder,
        shakoMakoApplication: ShakoMakoApplication
    ): Retrofit.Builder {

        return Retrofit.Builder()
            .baseUrl(shakoMakoApplication.resources.getString(R.string.base_url))
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
    }

    @Provides
    @Singleton
    fun providerApiResponse(
        builder: Retrofit.Builder,
        clientBuilder: OkHttpClient.Builder,
        userSession: UserSession
    ): ApiRepository {
        return ApiRepository(builder, clientBuilder, userSession)
    }
}