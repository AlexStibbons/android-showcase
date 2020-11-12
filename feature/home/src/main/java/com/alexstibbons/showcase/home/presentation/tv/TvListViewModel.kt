package com.alexstibbons.showcase.home.presentation.tv

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaList
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.interactors.GetTv
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.home.presentation.films.FilmListViewModel
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.search.SearchTerms

internal class TvListViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private var currentPage = 0
    private var searchTerms: SearchTerms? = null

    private val _state = MutableLiveData<TvListState>()
    fun observeFilms(): LiveData<TvListState> = _state

    init {
        _state.value = TvListState.Loading
        fetchTv()
    }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }

    fun fetchTv() {
        currentPage += 1
        interactor.getTv(GetTv.Params(currentPage, searchTerms)) { response ->
            when (response) {
                is Response.Failure -> renderError(response.failure)
                is Response.Success -> renderSuccess(response.success)
            }.exhaustive
        }
    }

    private fun renderSuccess(success: MediaList) {
        _state.value = TvListState.Success(success)
    }

    private fun renderError(failure: Failure) {
        val state = when (failure) {
            is Failure.ServerError -> TvListState.Error.ServerError
            is Failure.NetworkConnection ->  TvListState.Error.NoInternet
            is MediaFailure.NoSuchMedia, MediaFailure.EmptyMediaList ->  TvListState.Error.EmptyList
            is Failure.FeatureSpecificFailure -> error("Feature failure must be implemented")
        }.exhaustive

        _state.value = state
    }

    fun onClearSearch() {
        currentPage = 0
        searchTerms = null
        fetchTv()
    }

    fun onStartSearch(data: SearchTerms) {
        _state.value = TvListState.OnStartSearch
        currentPage = 0
        searchTerms = data
        fetchTv()
    }


    sealed class TvListState {
        object Loading: TvListState()
        object OnStartSearch : TvListState()
        data class Success(val data: MediaList): TvListState()
        sealed class Error(@StringRes val message: Int) : TvListState() {
            object NoInternet : Error(R.string.error_no_internet)
            object EmptyList: Error(R.string.error_empty_list)
            object ServerError: Error(R.string.error_server)
        }
    }

}