package com.alexstibbons.showcase.details

import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.details.domain.interactor.GetFilmDetails
import com.alexstibbons.showcase.details.domain.interactor.GetTvDetails
import com.alexstibbons.showcase.details.domain.interactor.Interactor
import com.alexstibbons.showcase.details.presentation.MediaDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal fun injectFeature() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(
        detailsPresentationModule,
        detailsDomainModule
    ))
}

private val detailsPresentationModule = module {
    viewModel { (mediaTypeId: Int, mediaId: Int) ->
        MediaDetailsViewModel(
            MediaType.from(mediaTypeId),
            mediaId,
            get()
        )
    }
}

private val detailsDomainModule = module {
    factory { GetFilmDetails(get(), get()) }
    factory { GetTvDetails(get(), get()) }

    factory { Interactor(get(), get(), get(), get(), get()) }
}