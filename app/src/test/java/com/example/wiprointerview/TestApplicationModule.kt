package com.example.wiprointerview

import android.app.Application
import com.example.wiprointerview.network.ApiRequest
import com.example.wiprointerview.network.DataModule
import com.example.wiprointerview.repository.MainRepository
import dagger.Component
import dagger.Module
import io.mockk.mockk
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


class TestApplicationModule(application: Application) : DataModule(application) {

    override fun provideHttpClient(): OkHttpClient = mockk()

    override fun provideItemService(retrofit: Retrofit): ApiRequest= mockk()

    override fun provideRepository(apiRequest: ApiRequest): MainRepository= mockk()

    override fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = mockk()

    override fun giveAppAPIs(): ApiRequest = mockk()
}