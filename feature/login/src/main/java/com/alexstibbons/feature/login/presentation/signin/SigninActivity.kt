package com.alexstibbons.feature.login.presentation.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.alexstibbons.feature.login.R
import com.alexstibbons.feature.login.databinding.ActivitySigninBinding
import com.alexstibbons.feature.login.injectFeature
import com.alexstibbons.showcase.LoginDataPoint
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.onAfterCredentialsInput
import com.alexstibbons.showcase.showToast
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val signinViewModel: SigninViewModel by viewModel()
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        signinViewModel.observeState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

        with(binding) {

            emailInput.onAfterCredentialsInput(R.string.error_email, LoginDataPoint.EMAIL) { text ->
                signinViewModel.addLoginData(LoginDataPoint.EMAIL, text)
                onInput()
            }

            passwordInput.onAfterCredentialsInput(R.string.error_password, LoginDataPoint.PASSWORD) { text ->
                signinViewModel.addLoginData(LoginDataPoint.PASSWORD, text)
                onInput()
            }

            btnSignup.setOnClickListener {
                startSignup()
            }

        }
    }

    private fun renderState(state: SigninViewModel.SignupState) = when (state) {
        SigninViewModel.SignupState.Failure -> {
            hideLoading()
            showToast("failure")
        }
        is SigninViewModel.SignupState.OpenHome -> {
            hideLoading()
            startActivity(NavigateTo.movieList(this, state.faveIds))
            finish()
        }
        SigninViewModel.SignupState.Loading -> showLoading()
    }.exhaustive

    private fun onInput() {
        binding.btnSignup.isEnabled = signinViewModel.isInputValid
    }

    private fun hideLoading() = with(binding) { loading.isVisible = false }

    private fun showLoading() = with(binding) { loading.isVisible = true }

    private fun startSignup() {
        val email = signinViewModel.loginData[LoginDataPoint.EMAIL] ?: ""
        val password = signinViewModel.loginData[LoginDataPoint.PASSWORD] ?: ""

        signinViewModel.onStartSignup()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    signinViewModel.onUserSignedUp(user)
                } else {
                    showToast("failure")
                }
            }
    }
}