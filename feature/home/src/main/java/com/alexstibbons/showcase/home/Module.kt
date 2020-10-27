package com.alexstibbons.showcase.home

import com.alexstibbons.showcase.home.presentation.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadModules

private val loadModules by lazy {
    loadKoinModules(
        listOf(
            homePresentationModule
        )
    )
}

private val homePresentationModule = module {
    viewModel { HomeViewModel() }
}