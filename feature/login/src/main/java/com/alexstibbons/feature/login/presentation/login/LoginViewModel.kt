package com.alexstibbons.feature.login.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.library.common_domain.SaveUserId
import com.alexstibbons.showcase.LoginDataPoint
import com.alexstibbons.showcase.database.domain.GetFaveIds
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.isEmailValid
import com.alexstibbons.showcase.isPasswordValid
import com.alexstibbons.showcase.responses.Response
import com.google.firebase.auth.FirebaseUser

internal class LoginViewModel(
    private val saveUserId: SaveUserId,
    private val getCachedFaves: GetFaveIds
) : ViewModel() {

    private val _loginState: MutableLiveData<LoginState> = MutableLiveData()
    fun observeState(): LiveData<LoginState> = _loginState

    private val _loginData = mutableMapOf<LoginDataPoint, String>()
    val loginData get() = _loginData

    val isInputValid : Boolean get() {
        val email = loginData[LoginDataPoint.EMAIL] ?: ""
        val password = loginData[LoginDataPoint.PASSWORD] ?: ""

        return (email.isEmailValid && password.isPasswordValid)
    }

    fun addLoginData(dataPoint: LoginDataPoint, data: String) {
        _loginData[dataPoint] = data
    }

    override fun onCleared() {
        saveUserId.cancel()
        getCachedFaves.cancel()
        super.onCleared()
    }

    fun onLoginSuccess(user: FirebaseUser?) {
        user?.let { user ->
            saveUserId(user.uid) { response ->
                 when (response) {
                    is Response.Failure -> _loginState.value = LoginState.Failure
                    is Response.Success -> onSaveUserSuccess()
                }.exhaustive
            }
        }
    }

    private fun onSaveUserSuccess() {
        getCachedFaves { response ->
            when (response) {
                is Response.Failure -> _loginState.value = LoginState.Failure
                is Response.Success -> {
                    val list = ArrayList<Int>()
                    response.success.toIntArray().toCollection(list)
                    _loginState.value = LoginState.OpenHome(list)
                }
            }.exhaustive
        }

    }

    fun onStartLogin() {
        _loginState.value = LoginState.Loading
    }

    sealed class LoginState {
        object Loading: LoginState()
        object Failure : LoginState()
        class OpenHome(val faveIds: ArrayList<Int>): LoginState()
    }

}