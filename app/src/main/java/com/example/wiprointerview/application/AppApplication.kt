package com.example.wiprointerview.application

import android.app.Application
import androidx.multidex.MultiDex
import com.example.wiprointerview.dependencyinjection.DaggerDataComponent
import com.example.wiprointerview.dependencyinjection.DataComponent
import com.example.wiprointerview.network.DataModule

class AppApplication : Application() {
    companion object {
        lateinit var app: AppApplication
        fun getApplication(): AppApplication {
            return app
        }
    }

    lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        app = this
        initDataComponent()
        dataComponent.inject(this)
    }

    private fun initDataComponent() {

        dataComponent = DaggerDataComponent.builder()
            .dataModule(DataModule(app))
            .build()
    }
}