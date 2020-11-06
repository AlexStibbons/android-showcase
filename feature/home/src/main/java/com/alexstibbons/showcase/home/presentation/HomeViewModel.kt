package com.alexstibbons.showcase.home.presentation

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class HomeViewModel(
    private val interactor: Interactor
) : ViewModel() {


    private val _state = MutableLiveData<ViewState>()
    fun observeState(): LiveData<ViewState> = _state


    private fun renderError(failure: Failure) {
        val state = when (failure) {
            is Failure.ServerError -> ViewState.Error.ServerError
            is Failure.NetworkConnection -> ViewState.Error.NoInternet
            is MediaFailure.NoSuchMedia, MediaFailure.EmptyMediaList -> ViewState.Error.EmptyList
            is Failure.FeatureSpecificFailure -> error("Feature failure must be implemented")
        }.exhaustive

        _state.value = state
    }

    fun addFave(media: MediaModel) {
        interactor.saveFave(media.toFaveEntity()) {response ->
            Log.e("response to save is", "$response")
        }
    }

    fun removeFave(id: Int) {
        interactor.removeFave(id) {response ->
            Log.e("removing fave response", "$response")
        }
    }

    override fun onCleared() {
        interactor.clear()
        super.onCleared()
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