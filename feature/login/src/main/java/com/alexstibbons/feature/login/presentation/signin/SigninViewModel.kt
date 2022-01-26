package com.alexstibbons.feature.login.presentation.signin

import androidx.lifecycle.ViewModel
import com.alexstibbons.feature.login.presentation.LoginDataPoint
import com.alexstibbons.feature.login.presentation.isEmailValid
import com.alexstibbons.feature.login.presentation.isPasswordValid

internal class SigninViewModel : ViewModel() {

    private val _loginData = mutableMapOf<LoginDataPoint, String>()
    val loginData get() = _loginData

    val isInputValid : Boolean get() {
        val email = loginData[LoginDataPoint.EMAIL] ?: ""
        val password = loginData[LoginDataPoint.PASSWORD] ?: ""

        return (email.isEmailValid && password.isPasswordValid)
    }


    init {

    }

    fun addLoginData(dataPoint: LoginDataPoint, data: String) {
        _loginData[dataPoint] = data
    }

    override fun onCleared() {

        super.onCleared()
    }
}