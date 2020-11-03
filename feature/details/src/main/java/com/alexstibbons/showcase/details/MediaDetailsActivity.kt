package com.alexstibbons.showcase.details

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.argumentOrThrow
import com.alexstibbons.showcase.details.domain.MediaModel
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_ID
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.activity_media_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class MediaDetailsActivity : ColoredSysBarActivity() {

    override val systemBarColor: Int
        get() = R.color.white

    private val mediaTypeId: Int by argumentOrThrow(MEDIA_TYPE_ID)
    private val mediaId: Int by argumentOrThrow(MEDIA_ID)

    private val detailsViewModel: MediaDetailsViewModel by viewModel { parametersOf(mediaTypeId, mediaId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_details)

        injectFeature()

        detailsViewModel.observeViewState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })
    }

    private fun renderState(state: MediaDetailsViewModel.ViewState) {
        when (state) {
            is MediaDetailsViewModel.ViewState.Loading -> showLoading()
            is MediaDetailsViewModel.ViewState.Success -> populateViews(state.data)
            is MediaDetailsViewModel.ViewState.Error -> showErrorMessage(state.message)
        }.exhaustive
    }

    private fun populateViews(data: MediaModel) {
        details_title.text = data.title
        details_overview.text = data.overview
        data.imdbUrl?.let {
            details_imdb.text = data.imdbUrl
            details_imdb.isVisible = true
        }
        hideLoading()
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showErrorMessage(@StringRes message: Int) {
        hideLoading()
        showToast(message)
    }
}