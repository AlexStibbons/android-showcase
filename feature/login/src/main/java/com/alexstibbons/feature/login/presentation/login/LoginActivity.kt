package com.alexstibbons.feature.login.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.alexstibbons.feature.login.R
import com.alexstibbons.feature.login.databinding.ActivityLoginBinding
import com.alexstibbons.feature.login.injectFeature
import com.alexstibbons.feature.login.presentation.LoginDataPoint
import com.alexstibbons.feature.login.presentation.isEmailValid
import com.alexstibbons.feature.login.presentation.isPasswordValid
import com.alexstibbons.showcase.doAfterTextChange
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        with(binding) {

            btnSignin.setOnClickListener {
                startActivity(NavigateTo.signin(this@LoginActivity))
                finish()
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
                showToast("start login")
            }

        }
    }

    private fun onInput() {
        binding.btnLogin.isEnabled = loginViewModel.isInputValid
    }
}