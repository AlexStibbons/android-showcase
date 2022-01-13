package com.alexstibbons.showcase

import android.os.Bundle
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int
        get() = R.color.colorPrimary

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.observeScreen().observe(this, Observer { state ->
            state ?: return@Observer

            startActivity(state.intent(this))
            finish()
        })

    }
}