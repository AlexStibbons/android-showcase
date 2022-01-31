package com.alexstibbons.feature.login.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alexstibbons.feature.login.R
import com.alexstibbons.feature.login.databinding.ActivityLoginBinding
import com.alexstibbons.feature.login.injectFeature
import com.alexstibbons.feature.login.presentation.LoginDataPoint
import com.alexstibbons.feature.login.presentation.isEmailValid
import com.alexstibbons.feature.login.presentation.onAfterCredentialsInput
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.showToast
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        loginViewModel.observeState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

        with(binding) {

            btnSignin.setOnClickListener {
                startActivity(NavigateTo.signin(this@LoginActivity))
                finish()
            }

            btnReset.setOnClickListener {
                startResetPassword()
            }

            emailInput.onAfterCredentialsInput(R.string.error_email, LoginDataPoint.EMAIL) { text ->
                loginViewModel.addLoginData(LoginDataPoint.EMAIL, text)
                onInput()
            }

            passwordInput.onAfterCredentialsInput(R.string.error_password, LoginDataPoint.PASSWORD) { text ->
                loginViewModel.addLoginData(LoginDataPoint.PASSWORD, text)
                onInput()
            }

            btnLogin.setOnClickListener {
                startLogin()
            }

        }
    }

    private fun renderState(state: LoginViewModel.LoginState) = when (state) {
        LoginViewModel.LoginState.Failure -> showToast(R.string.error_login_save)
        is LoginViewModel.LoginState.OpenHome -> {
            startActivity(NavigateTo.movieList(this, state.faveIds))
            finish()
        }
    }.exhaustive

    private fun startResetPassword() {
        val email = loginViewModel.loginData[LoginDataPoint.EMAIL] ?: ""
        if (!email.isEmailValid) {
            binding.emailInput.error = getString(R.string.error_email)
        } else {
            binding.emailInput.error = null
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast(R.string.email_sent)
                    }
                }
        }
    }

    private fun onInput() {
        binding.btnLogin.isEnabled = loginViewModel.isInputValid
    }

    private fun startLogin() {
        val email = loginViewModel.loginData[LoginDataPoint.EMAIL] ?: ""
        val password = loginViewModel.loginData[LoginDataPoint.PASSWORD] ?: ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    loginViewModel.onLoginSuccess(user)
                } else {
                    showToast(R.string.error_login)
                }
            }
    }
}