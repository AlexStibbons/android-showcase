package com.alexstibbons.showcase.details

import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.details.domain.MediaModel
import com.alexstibbons.showcase.details.domain.interactor.Interactor
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class MediaDetailsViewModel(
    private val mediaType: MediaType,
    private val mediaId: Int,
    private val interactor: Interactor
) : ViewModel() {

    private var _viewState = MutableLiveData<ViewState>()
    fun observeViewState(): LiveData<ViewState> = _viewState

    init {
        _viewState.value = ViewState.Loading
        fetchDetailsFor(mediaType)
    }

    private fun fetchDetailsFor(mediaType: MediaType) {
        when (mediaType) {
            MediaType.FILM -> fetchFilmDetails()
            MediaType.TV -> fetchTvDetails()
            MediaType.FAVE -> {}
        }.exhaustive
    }

    private fun fetchTvDetails() {
        interactor.getTvDetails(mediaId) { response ->
            parseResponse(response)
        }
    }

    private fun parseResponse(response: Response<Failure, MediaModel>) {
        when (response) {
            is Response.Failure -> renderError(response.failure)
            is Response.Success -> _viewState.value = ViewState.Success(response.success)
        }.exhaustive
    }

    private fun renderError(failure: Failure) {
        val error = when (failure) {
            is Failure.ServerError -> ViewState.Error.ServerError
            is Failure.NetworkConnection -> ViewState.Error.NoInternet
            is MediaFailure.EmptyMediaList, MediaFailure.NoSuchMedia-> ViewState.Error.NoSuchMedia
            is Failure.FeatureSpecificFailure -> error("Feature failure must be implemented")
        }.exhaustive

        _viewState.value = error
    }

    private fun fetchFilmDetails() {
        interactor.getFilmDetails(mediaId) { response ->
            parseResponse(response)
        }
    }


    sealed class ViewState {
        object Loading: ViewState()
        data class Success(val data: MediaModel): ViewState()
        sealed class Error(@StringRes val message: Int) : ViewState() {
            object NoInternet : Error(R.string.error_no_internet)
            object NoSuchMedia : Error(R.string.error_empty_list)
            object ServerError : Error(R.string.error_server)
        }
    }
}