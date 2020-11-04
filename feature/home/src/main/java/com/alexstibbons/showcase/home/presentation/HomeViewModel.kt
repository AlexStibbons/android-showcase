package com.alexstibbons.showcase.home.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaList
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class HomeViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private var currentPage = 0
    private var currentType = MediaType.FILM

    private val _state = MutableLiveData<ViewState>()
    fun observeState(): LiveData<ViewState> = _state


    fun setCurrentType(type: MediaType) {
        currentType = type
    }

    fun fetchMediaList() {
        currentPage += 1
        when (currentType) {
            MediaType.FILM -> fetchFilms(currentPage)
            MediaType.TV -> fetchTv(currentPage)
            MediaType.FAVE -> fetchFaves(currentPage)
        }.exhaustive
    }


    private fun renderSuccess(success: MediaList) {
        _state.value = ViewState.Success(success)
    }

    private fun renderError(failure: Failure) {
        val state = when (failure) {
            is Failure.ServerError -> ViewState.Error.ServerError
            is Failure.NetworkConnection -> ViewState.Error.NoInternet
            is MediaFailure.NoSuchMedia, MediaFailure.EmptyMediaList -> ViewState.Error.EmptyList
            is Failure.FeatureSpecificFailure -> error("Feature failure must be implemented")
        }.exhaustive

        _state.value = state
    }

    private fun fetchTv(page: Int) {
        interactor.getTv(page) { response ->
            when (response) {
                is Response.Failure -> renderError(response.failure)
                is Response.Success -> renderSuccess(response.success)
            }.exhaustive
        }
    }

    private fun fetchFilms(page: Int) {
        interactor.getFilms(page) { response ->
            when (response) {
                is Response.Failure -> renderError(response.failure)
                is Response.Success -> renderSuccess(response.success)
            }.exhaustive
        }
    }

    private fun fetchFaves(page: Int) {

    }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }

    fun resetCurrentPage() {
        currentPage = 0
    }

    sealed class ViewState {
        object Loading: ViewState()
        data class Success(val data: MediaList): ViewState()
        sealed class Error(@StringRes val message: Int) : ViewState() {
            object NoInternet : Error(R.string.error_no_internet)
            object EmptyList: Error(R.string.error_empty_list)
            object ServerError: Error(R.string.error_server)
        }
    }
}