package com.webslinger.dejavu.application

import android.app.Application
import timber.log.Timber

class DejavuApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}