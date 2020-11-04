package com.alexstibbons.showcase.home

import com.alexstibbons.showcase.home.domain.interactors.GetFilms
import com.alexstibbons.showcase.home.domain.interactors.GetTv
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.home.presentation.HomeViewModel
import com.alexstibbons.showcase.home.presentation.films.FilmListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadModules

private val loadModules by lazy {
    loadKoinModules(
        listOf(
            homePresentationModule,
            homeDomainModule
        )
    )
}

private val homePresentationModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FilmListViewModel(get()) }
}

private val homeDomainModule = module {
    factory { GetTv(get(), get()) }
    factory { GetFilms(get(), get()) }

    factory { Interactor(get(), get()) }
}