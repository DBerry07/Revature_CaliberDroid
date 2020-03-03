package com.revature.caliberdroid.application

import android.app.Application
import timber.log.Timber

class CaliberApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}