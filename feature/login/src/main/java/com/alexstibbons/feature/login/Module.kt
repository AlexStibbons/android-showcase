package com.alexstibbons.feature.login

import com.alexstibbons.feature.login.presentation.login.LoginViewModel
import com.alexstibbons.feature.login.presentation.signin.SigninViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal fun injectFeature() = loadModule

private val loadModule by lazy {
    loadKoinModules(listOf(
        loginDomain,
        loginPresentation
    ))
}

private val loginDomain = module {

    single<FirebaseAuth> { Firebase.auth }
}

private val loginPresentation = module {
    viewModel { LoginViewModel() }
    viewModel { SigninViewModel() }
}
