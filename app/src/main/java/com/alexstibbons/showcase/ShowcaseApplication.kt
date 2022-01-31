package com.alexstibbons.showcase

import android.app.Application
import com.alexstibbons.library.common_domain.commonDomainModule
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
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ShowcaseApplication)
            androidFileProperties()
            loadKoinModules(
                listOf(
                    appModule,
                    networkModule,
                    repositoryModule,
                    commonDomainModule,
                    openLinkModule
                )
            )
        }
    }
}