package org.app.marcelodev.comucarona

import android.app.Application
import org.app.marcelodev.comucarona.di.initKoin
import org.app.marcelodev.comucarona.service.sharedpreferences.appContext
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class ComuCaronaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this@ComuCaronaApplication
        initKoin {
            androidLogger()
            androidContext(this@ComuCaronaApplication)
        }
    }
}