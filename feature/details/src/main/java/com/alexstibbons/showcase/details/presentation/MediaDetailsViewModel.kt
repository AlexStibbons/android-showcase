package com.alexstibbons.showcase.details.presentation

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.details.R
import com.alexstibbons.showcase.details.domain.MediaDetailsModel
import com.alexstibbons.showcase.details.domain.interactor.Interactor
import com.alexstibbons.showcase.details.domain.toFaveEntity
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class MediaDetailsViewModel(
    private val mediaType: MediaType,
    private val mediaId: Int,
    private val interactor: Interactor
) : ViewModel() {

    private val cachedFaveIds = mutableListOf<Int>()

    private var _viewState = MutableLiveData<ViewState>()
    fun observeViewState(): LiveData<ViewState> = _viewState

    init {
        _viewState.value =
            ViewState.Loading
        fetchFaveIds()
    }

    fun isFave() = cachedFaveIds.contains(mediaId)

    fun addFave(data: MediaDetailsModel) {
        interactor.saveFave(data.toFaveEntity()) {response ->
            Log.e("save from details", "$response")
        }
    }

    fun removeFave(id: Int) {
        interactor.removeFave(id) {response ->
            Log.e("remove from details", "$response")
        }
    }

    private fun fetchFaveIds() {
        interactor.getFaves {response ->
            cachedFaveIds.addAll((response as Response.Success).success)
            fetchDetailsFor(mediaType)
        }
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

    private fun parseResponse(response: Response<Failure, MediaDetailsModel>) {
        when (response) {
            is Response.Failure -> renderError(response.failure)
            is Response.Success -> _viewState.value =
                ViewState.Success(
                    response.success
                )
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

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }


    sealed class ViewState {
        object Loading: ViewState()
        data class Success(val data: MediaDetailsModel): ViewState()
        sealed class Error(@StringRes val message: Int) : ViewState() {
            object NoInternet : Error(
                R.string.error_no_internet
            )
            object NoSuchMedia : Error(
                R.string.error_empty_list
            )
            object ServerError : Error(
                R.string.error_server
            )
        }
    }
}