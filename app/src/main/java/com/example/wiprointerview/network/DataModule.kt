package com.example.wiprointerview.network

import android.app.Application
import android.content.Context
import com.example.wiprointerview.repository.MainRepository
import com.example.wiprointerview.util.NetworkUtils
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
open class DataModule(var application: Application) {

    companion object {
        const val BASE_URL = "https://dl.dropboxusercontent.com/"
    }
    @Provides
    @Singleton
    fun giveContext(): Context = this.application

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(giveContext().cacheDir, cacheSize)
    private val okHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor {chain->
            var request = chain.request()
            request = if (NetworkUtils.hasNetwork(application)) {
               request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            } else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
               chain.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    open fun provideHttpClient(): OkHttpClient {
        return okHttpClient
    }

    @Provides
    @Singleton
    open fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    open fun provideItemService(retrofit: Retrofit): ApiRequest {
        return retrofit.create(ApiRequest::class.java)
    }
    @Provides
    @Named("test")
    open fun giveAppAPIs(): ApiRequest =
        Retrofit.Builder().build().create(ApiRequest::class.java)

    @Singleton
    @Provides
    open fun provideRepository(apiRequest: ApiRequest): MainRepository {
        return MainRepository(apiRequest)
    }


}