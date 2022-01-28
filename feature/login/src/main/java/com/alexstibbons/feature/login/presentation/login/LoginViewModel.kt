package com.alexstibbons.feature.login.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.feature.login.presentation.LoginDataPoint
import com.alexstibbons.feature.login.presentation.isEmailValid
import com.alexstibbons.feature.login.presentation.isPasswordValid
import com.alexstibbons.library.common_domain.SaveUserId
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.responses.Response
import com.google.firebase.auth.FirebaseUser

internal class LoginViewModel(
    private val saveUserId: SaveUserId
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
        super.onCleared()
    }

    fun onLoginSuccess(user: FirebaseUser?) {
        user?.let { user ->
            saveUserId(user.uid) { response ->
                _loginState.value = when (response) {
                    is Response.Failure -> LoginState.Failure
                    is Response.Success -> LoginState.UserIdSaved
                }.exhaustive
            }
        }
    }

    sealed class LoginState {
        object UserIdSaved : LoginState()
        object Failure : LoginState()
    }

}