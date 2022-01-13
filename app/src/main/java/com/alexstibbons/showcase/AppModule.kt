package com.alexstibbons.showcase

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    viewModel { SplashViewModel(getFaveIds = get()) }
}