package com.alexstibbons.showcase.details

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_ID
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.MEDIA_TYPE_ID
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class MediaDetailsActivity : ColoredSysBarActivity() {

    override val systemBarColor: Int
        get() = R.color.white

    private val mediaTypeId: Int by argumentOrThrow(MEDIA_TYPE_ID)
    private val mediaType: MediaType = MediaType.from(mediaTypeId)
    private val mediaId: Int by argumentOrThrow(MEDIA_ID)

    private val detailsViewModel: MediaDetailsViewModel by viewModel { parametersOf(mediaType, mediaId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_details)

        injectFeature()

    }
}


fun <T : Any> FragmentActivity.argumentOrThrow(key: String): Lazy<T> = lazy {
    intent.extras?.get(key) as? T
        ?: throw IllegalStateException("Bundle needs to have argument with id: $key")
}