package com.alexstibbons.showcase

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ShowcaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@ShowcaseApplication)
            androidFileProperties()
            loadKoinModules(
                listOf(
                    networkModule,
                    repositoryModule
                )
            )
        }
    }
}