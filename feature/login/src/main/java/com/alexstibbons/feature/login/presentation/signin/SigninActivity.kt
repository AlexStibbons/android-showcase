package com.alexstibbons.feature.login.presentation.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexstibbons.feature.login.databinding.ActivitySigninBinding
import com.alexstibbons.feature.login.injectFeature
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val signinViewModel: SigninViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

    }
}