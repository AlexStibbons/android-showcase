package com.alexstibbons.showcase.home

import com.alexstibbons.showcase.home.domain.interactors.GetFilms
import com.alexstibbons.showcase.home.domain.interactors.GetTv
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.home.presentation.HomeViewModel
import com.alexstibbons.showcase.home.presentation.faves.FaveListViewModel
import com.alexstibbons.showcase.home.presentation.films.FilmListViewModel
import com.alexstibbons.showcase.home.presentation.tv.TvListViewModel
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
    viewModel { (faveIds: List<Int>) -> HomeViewModel(get(), faveIds) }
    viewModel { FilmListViewModel(get()) }
    viewModel { TvListViewModel(get()) }
    viewModel { FaveListViewModel(get()) }
}

private val homeDomainModule = module {
    factory { GetTv(get(), get()) }
    factory { GetFilms(get(), get()) }

    factory { Interactor(get(), get(), get(), get(), get(), get()) }
}