package com.alexstibbons.feature.login.presentation.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexstibbons.feature.login.databinding.ActivityLoginBinding
import com.alexstibbons.feature.login.injectFeature
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()
    }
}