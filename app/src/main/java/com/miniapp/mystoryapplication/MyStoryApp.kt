package com.miniapp.mystoryapplication

import android.app.Application
import com.miniapp.mystoryapplication.core.di.apiModule
import com.miniapp.mystoryapplication.core.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("Unused")
class MyStoryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MyStoryApp)
            modules(
                networkModule,
                apiModule
            )
        }
    }
}