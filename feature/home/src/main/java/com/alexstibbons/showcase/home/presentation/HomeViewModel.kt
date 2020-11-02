package com.alexstibbons.showcase.home.presentation

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.MediaType
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.MovieListDomain
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.movieApi.MovieFailure
import com.alexstibbons.showcase.movieApi.model.Movie
import com.alexstibbons.showcase.movieApi.model.MovieListResponse
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class HomeViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private val _state = MutableLiveData<ViewState>()
    fun observeState(): LiveData<ViewState> = _state


    fun fetchMediaList(type: MediaType, page: Int = 1) {
        when (type) {
            MediaType.FILM -> fetchFilms(page)
            MediaType.TV -> fetchTv(page)
            MediaType.FAVE -> fetchFaves(page)
        }.exhaustive
    }


    private fun renderSuccess(success: MovieListDomain) {
        _state.value = ViewState.Success(success)
    }

    private fun renderError(failure: Failure) {
        val state = when (failure) {
            is Failure.ServerError -> ViewState.Error.ServerError
            is Failure.NetworkConnection -> ViewState.Error.NoInternet
            is MovieFailure.NoSuchMovie, MovieFailure.EmptyMovieList -> ViewState.Error.EmptyList
            is Failure.FeatureSpecificFailure -> error("Feature failure must be implemented")
        }.exhaustive

        _state.value = state
    }

    private fun fetchTv(page: Int) {

    }

    private fun fetchFilms(page: Int) {
        interactor.getFilms { response ->
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

    sealed class ViewState {
        object Loading: ViewState()
        data class Success(val data: MovieListDomain): ViewState()
        sealed class Error(@StringRes val message: Int) : ViewState() {
            object NoInternet : Error(R.string.error_no_internet)
            object EmptyList: Error(R.string.error_empty_list)
            object ServerError: Error(R.string.error_server)
        }
    }
}