package com.alexstibbons.showcase.details

import com.alexstibbons.showcase.MediaType
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal fun injectFeature() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(
        detailsPresentationModule
    ))
}

private val detailsPresentationModule = module {
    viewModel { (mediaTypeId: Int, mediaId: Int) -> MediaDetailsViewModel(MediaType.from(mediaTypeId), mediaId) }
}