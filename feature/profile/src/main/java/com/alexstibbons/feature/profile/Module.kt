package com.alexstibbons.feature.profile

import com.alexstibbons.feature.profile.presentation.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single<FirebaseAuth> { Firebase.auth }
}

private val presentationModule = module {
    viewModel { ProfileViewModel(get()) }
}