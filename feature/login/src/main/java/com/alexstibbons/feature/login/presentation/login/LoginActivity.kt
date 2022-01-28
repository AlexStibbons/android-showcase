package com.alexstibbons.feature.login.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.alexstibbons.feature.login.R
import com.alexstibbons.feature.login.databinding.ActivityLoginBinding
import com.alexstibbons.feature.login.injectFeature
import com.alexstibbons.feature.login.presentation.LoginDataPoint
import com.alexstibbons.feature.login.presentation.isEmailValid
import com.alexstibbons.feature.login.presentation.isPasswordValid
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

            emailInput.doAfterTextChanged { input ->
                val text = input.toString()
                loginViewModel.addLoginData(LoginDataPoint.EMAIL, text)
                onInput()
                if (!text.isEmailValid) {
                    emailInput.error = getString(R.string.error_email)
                } else {
                    emailInput.error = null
                }
            }

            passwordInput.doAfterTextChanged { input ->
                val text = input.toString()
                loginViewModel.addLoginData(LoginDataPoint.PASSWORD, text)
                onInput()
                if (!text.isPasswordValid) {
                    passwordInput.error = getString(R.string.error_password)
                } else {
                    passwordInput.error = null
                }
            }

            btnLogin.setOnClickListener {
                startLogin()
            }

        }
    }

    private fun renderState(state: LoginViewModel.LoginState) = when (state) {
        LoginViewModel.LoginState.Failure -> showToast("failure to save")
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
                        showToast("email sent")
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
                    showToast("failure")
                }
            }
    }
}