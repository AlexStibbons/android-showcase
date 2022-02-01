package com.alexstibbons.feature.profile

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal fun injectFeature() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(
        domainModule,
        presentationModule
    ))
}

private val domainModule = module {

}

private val presentationModule = module {

}