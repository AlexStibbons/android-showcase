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
import com.alexstibbons.showcase.details.databinding.ActivityMediaDetailsBinding
import com.alexstibbons.showcase.details.domain.MediaDetailsModel
import com.alexstibbons.showcase.details.injectFeature
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_ID
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class MediaDetailsActivity : ColoredSysBarActivity() {

    private lateinit var detailsBinding: ActivityMediaDetailsBinding

    override val systemBarColor: Int
        get() = R.color.transparent

    private val mediaTypeId: Int by argumentOrThrow(MEDIA_TYPE_ID)
    private val mediaId: Int by argumentOrThrow(MEDIA_ID)

    private val detailsViewModel: MediaDetailsViewModel by viewModel { parametersOf(mediaTypeId, mediaId) }

    private val onLinkClick: LinkResource by inject { parametersOf(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = ActivityMediaDetailsBinding.inflate(layoutInflater)
        setContentView(detailsBinding.root)
        setTransparentSystemBar()

        injectFeature()

        detailsBinding.detailsBack.setOnClickListener { super.onBackPressed() }

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

    private fun populateViews(data: MediaDetailsModel) = with(detailsBinding) {
        detailsTitle.text = data.title
        detailsOverview.text = data.overview
        data.tagline?.let {
            detailsTagline.text = it
            detailsTagline.isVisible = true
        }
        data.imdbUrl?.let {
            detailsImdb.setOnClickListener { onLinkClick.openInBrowser(data.imdbUrl!!) }
            detailsImdb.isVisible = true
        }
        data.trailer?.let {
            detailsYoutube.setOnClickListener { onLinkClick.openInBrowser(data!!.trailer!!.youtubeLink) }
            detailsYoutube.isVisible = true
        }

        data.genres?.let { genres ->
            detailsGenres.text = genres.joinToString { it.title }
            detailsGenres.isVisible = true
        }

        data.imageUrl?.let {url ->
            Glide
                .with(this@MediaDetailsActivity)
                .asBitmap()
                .error(R.drawable.ic_logo_big)
                .load(BASE_IMG_URL+url)
                .centerInside()
                .into(detailsImage)
        }

        if (detailsViewModel.isFave()) detailsBtnFave.isChecked = true

        detailsBtnFave.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                detailsViewModel.addFave(data)
                detailsBtnFave.isChecked = true
                sendBroadcast(Intent("fave_change"))
            } else {
                detailsViewModel.removeFave(data.id)
                detailsBtnFave.isChecked = false
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