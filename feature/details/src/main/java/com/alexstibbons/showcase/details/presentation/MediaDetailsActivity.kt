package com.alexstibbons.showcase.details.presentation

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.details.R
import com.alexstibbons.showcase.details.domain.MediaDetailsModel
import com.alexstibbons.showcase.details.injectFeature
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_ID
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_media_details.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


internal class MediaDetailsActivity : ColoredSysBarActivity() {

    override val systemBarColor: Int
        get() = R.color.transparent

    private val mediaTypeId: Int by argumentOrThrow(MEDIA_TYPE_ID)
    private val mediaId: Int by argumentOrThrow(MEDIA_ID)

    private val detailsViewModel: MediaDetailsViewModel by viewModel { parametersOf(mediaTypeId, mediaId) }

    private val onLinkClick: LinkResource by inject { parametersOf(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_details)
        setTransparentSystemBar()

        injectFeature()

        details_back.setOnClickListener { super.onBackPressed() }

        detailsViewModel.observeViewState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })
    }

    private fun setTransparentSystemBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun renderState(state: MediaDetailsViewModel.ViewState) {
        when (state) {
            is MediaDetailsViewModel.ViewState.Loading -> showLoading()
            is MediaDetailsViewModel.ViewState.Success -> populateViews(state.data)
            is MediaDetailsViewModel.ViewState.Error -> showErrorMessage(state.message)
        }.exhaustive
    }

    private fun populateViews(data: MediaDetailsModel) {
        details_title.text = data.title
        details_overview.text = data.overview
        data.tagline?.let {
            details_tagline.text = it
            details_tagline.isVisible = true
        }
        data.imdbUrl?.let {
            details_imdb.setOnClickListener { onLinkClick.openInBrowser(data.imdbUrl!!) }
            details_imdb.isVisible = true
        }
        data.trailer?.let {
            details_youtube.setOnClickListener { onLinkClick.openInBrowser(data!!.trailer!!.youtubeLink) }
            details_youtube.isVisible = true
        }

        data.genres?.let { genres ->
            details_genres.text = genres.joinToString { it.title }
            details_genres.isVisible = true
        }

        data.imageUrl?.let {url ->
            Glide
                .with(this@MediaDetailsActivity)
                .asBitmap()
                .load(BASE_IMG_URL+url)
                .centerInside()
                .into(details_image)
        }

        if (detailsViewModel.isFave()) details_btn_fave.isChecked = true

        details_btn_fave.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                detailsViewModel.addFave(data)
                details_btn_fave.isChecked = true
                sendBroadcast(Intent("fave_change"))
            } else {
                detailsViewModel.removeFave(data.id)
                details_btn_fave.isChecked = false
                sendBroadcast(Intent("fave_change"))
            }
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