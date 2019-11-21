package com.example.wiprointerview

import com.example.wiprointerview.dependencyinjection.DataComponent
import com.example.wiprointerview.network.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DataModule::class)])
interface DataComponentTest : DataComponent {
    fun inject(repository: AppRepositoryInjectTest?)
    fun inject(repository : MainRepositoryTest)

    
}