package com.alexstibbons.feature.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexstibbons.feature.profile.databinding.ActivityProfileBinding

internal class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        with(binding) {
            
        }
    }
}