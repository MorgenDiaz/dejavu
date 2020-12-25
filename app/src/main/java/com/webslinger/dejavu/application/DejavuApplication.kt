package com.webslinger.dejavu.application

import android.app.Application
import com.webslinger.dejavu.application.di.ApplicationComponent
import com.webslinger.dejavu.application.di.ApplicationModule
import com.webslinger.dejavu.application.di.DaggerApplicationComponent
import timber.log.Timber

class DejavuApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}