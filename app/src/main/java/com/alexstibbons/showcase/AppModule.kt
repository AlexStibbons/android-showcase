package com.alexstibbons.showcase

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    viewModel { SplashViewModel() }
}