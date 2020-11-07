package com.alexstibbons.showcase.home.presentation.faves

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.database.FaveEntity
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.domain.FaveList
import com.alexstibbons.showcase.home.domain.interactors.Interactor
import com.alexstibbons.showcase.home.domain.toMediaModel
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

internal class FaveListViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private var _state = MutableLiveData<ViewState>()
    fun observeState(): LiveData<ViewState> = _state

    init {
        _state.value = ViewState.Loading
        fetchFaves()
    }

    private fun fetchFaves() {
        interactor.getFaves {response ->
            renderResponse(response)
        }
    }

    private fun renderResponse(response: Response<Failure, List<FaveEntity>>) {
      when (response) {
            is Response.Failure -> _state.value = ViewState.Error.ServerError
            is Response.Success -> if (response.success.isEmpty()) ViewState.Error.EmptyList else transformData(response.success)
        }.exhaustive
    }

    private fun transformData(data: List<FaveEntity>) {
        val media = data.mapToListOf { it.toMediaModel() }
        _state.value = ViewState.Success(FaveList(1, media))
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