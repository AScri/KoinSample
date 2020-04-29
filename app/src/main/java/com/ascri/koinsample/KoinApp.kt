package com.ascri.koinsample

import android.app.Application
import com.ascri.koinsample.utils.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinApp)
            modules(appModules)
        }
    }
}