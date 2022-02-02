package com.alexstibbons.feature.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.library.common_domain.Logout

internal class ProfileViewModel(
    private val logout: Logout
): ViewModel() {

    private var _state: MutableLiveData<ViewState> = MutableLiveData()
    fun observeState(): LiveData<ViewState> = _state

    fun onLogout(doDeleteUser: Boolean) {
        _state.value = ViewState.Loading
        logout { response ->
            // response can only be a success
            _state.value = ViewState.LogoutSuccess(doDeleteUser)
        }
    }

    sealed class ViewState {
        class LogoutSuccess(val doDeleteUser: Boolean): ViewState()
        object Loading: ViewState()
    }
}