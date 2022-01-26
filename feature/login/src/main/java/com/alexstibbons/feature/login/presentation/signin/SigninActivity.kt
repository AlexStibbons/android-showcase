package com.alexstibbons.feature.login.presentation.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.alexstibbons.feature.login.R
import com.alexstibbons.feature.login.databinding.ActivitySigninBinding
import com.alexstibbons.feature.login.injectFeature
import com.alexstibbons.feature.login.presentation.LoginDataPoint
import com.alexstibbons.feature.login.presentation.isEmailValid
import com.alexstibbons.feature.login.presentation.isPasswordValid
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val signinViewModel: SigninViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        with(binding) {


            emailInput.doAfterTextChanged { input ->
                val text = input.toString()
                signinViewModel.addLoginData(LoginDataPoint.EMAIL, text)
                onInput()
                if (!text.isEmailValid) {
                    emailInput.error = getString(R.string.error_email)
                } else {
                    emailInput.error = null
                }
            }

            passwordInput.doAfterTextChanged { input ->
                val text = input.toString()
                signinViewModel.addLoginData(LoginDataPoint.PASSWORD, text)
                onInput()
                if (!text.isPasswordValid) {
                    passwordInput.error = getString(R.string.error_password)
                } else {
                    passwordInput.error = null
                }
            }

            btnSignup.setOnClickListener {
                showToast("start sign up")
            }

        }
    }

    private fun onInput() {
        binding.btnSignup.isEnabled = signinViewModel.isInputValid
    }
}