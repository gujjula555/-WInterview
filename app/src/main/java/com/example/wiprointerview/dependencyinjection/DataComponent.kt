package com.example.wiprointerview.dependencyinjection

import com.example.wiprointerview.application.AppApplication
import com.example.wiprointerview.network.DataModule
import com.example.wiprointerview.repository.MainRepository
import com.example.wiprointerview.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DataModule::class])
interface DataComponent {
    fun inject(repository: MainRepository?)
    fun inject(mainViewModel: MainViewModel?)
    fun inject(appApplication: AppApplication)
}