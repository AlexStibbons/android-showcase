package com.alexstibbons.showcase.home.presentation.films

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.MediaList
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.interactors.GetFilms
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.search.SearchTerms

internal class FilmListViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private var currentPage = 0
    private var searchTerms: SearchTerms? = null

    private val _state = MutableLiveData<FilmListState>()
    fun observeFilms(): LiveData<FilmListState> = _state

    init {
        _state.value = FilmListState.Loading
        fetchFilms()
    }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
    }

    fun fetchFilms() {
        currentPage += 1
        interactor.getFilms(GetFilms.Params(currentPage, searchTerms)) { response ->
            when (response) {
                is Response.Failure -> renderError(response.failure)
                is Response.Success -> renderSuccess(response.success)
            }.exhaustive
        }
    }

    private fun renderSuccess(success: MediaList) {
        _state.value = FilmListState.Success(success)
    }

    private fun renderError(failure: Failure) {
        val state = when (failure) {
            is Failure.ServerError -> FilmListState.Error.ServerError
            is Failure.NetworkConnection ->  FilmListState.Error.NoInternet
            is MediaFailure.NoSuchMedia, MediaFailure.EmptyMediaList ->  FilmListState.Error.EmptyList
            is Failure.FeatureSpecificFailure -> error("Feature failure must be implemented")
        }.exhaustive

        _state.value = state
    }

    fun onStartSearch(data: SearchTerms) {
        _state.value = FilmListState.OnStartSearch
        currentPage = 0
        searchTerms = data
        fetchFilms()
    }

    fun onClearSearch() {
        currentPage = 0
        searchTerms = null
        fetchFilms()
    }

    sealed class FilmListState {
        object Loading: FilmListState()
        object OnStartSearch: FilmListState()
        data class Success(val data: MediaList): FilmListState()
        sealed class Error(@StringRes val message: Int) : FilmListState() {
            object NoInternet : Error(R.string.error_no_internet)
            object EmptyList: Error(R.string.error_empty_list)
            object ServerError: Error(R.string.error_server)
        }
    }

}