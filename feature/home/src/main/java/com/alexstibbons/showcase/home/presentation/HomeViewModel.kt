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
    private val interactor: Interactor,
    private val initialFaveId: List<Int>
) : ViewModel() {

    private var _cachedFaveId = initialFaveId.toMutableList()

    fun cachedIds(): List<Int> = _cachedFaveId

    private val _state = MutableLiveData<ViewState>()
    fun observeState(): LiveData<ViewState> = _state


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

    fun updateCachedFaves() {
        interactor.getFaveIds { response ->
            val ids = (response as Response.Success).success
            _cachedFaveId.clear()
            _cachedFaveId.addAll(ids)
            _state.value = ViewState.NewFaves(ids)
        }
    }

    fun getUserId() = interactor.getUserId { response ->
        when (response) {
            is Response.Failure -> Log.e("home vm", "user id failed")
            is Response.Success -> Log.e("home vm", "user id is ${response.success}")
        }.exhaustive
    }

    sealed class ViewState {
        object Loading: ViewState()
        data class NewFaves(val data: List<Int>): ViewState()
        sealed class Error(@StringRes val message: Int) : ViewState() {
            object NoInternet : Error(R.string.error_no_internet)
            object EmptyList: Error(R.string.error_empty_list)
            object ServerError: Error(R.string.error_server)
        }
    }
}