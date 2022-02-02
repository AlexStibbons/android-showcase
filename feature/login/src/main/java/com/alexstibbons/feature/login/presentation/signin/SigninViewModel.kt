package com.alexstibbons.feature.login.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexstibbons.library.common_domain.SaveUserId
import com.alexstibbons.showcase.LoginDataPoint
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.isEmailValid
import com.alexstibbons.showcase.isPasswordValid
import com.alexstibbons.showcase.responses.Response
import com.google.firebase.auth.FirebaseUser

internal class SigninViewModel(
    private val saveUserId: SaveUserId
) : ViewModel() {

    private val _signupState: MutableLiveData<SignupState> = MutableLiveData()
    fun observeState(): LiveData<SignupState> = _signupState

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

    fun onUserSignedUp(user: FirebaseUser?) {
        user?.let {
            saveUserId(user.uid) { response ->
                _signupState.value = when (response) {
                    is Response.Failure -> SignupState.Failure
                    is Response.Success -> SignupState.OpenHome()
                }.exhaustive
            }
        }
    }

    fun onStartSignup() {
        _signupState.value = SignupState.Loading
    }

    sealed class SignupState {
        object Loading : SignupState()
        object Failure : SignupState()
        class OpenHome(val faveIds: ArrayList<Int> = ArrayList()): SignupState()
    }
}