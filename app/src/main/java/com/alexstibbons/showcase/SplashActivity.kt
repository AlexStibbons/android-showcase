package com.alexstibbons.showcase

import android.os.Bundle
import androidx.lifecycle.Observer
import com.alexstibbons.showcase.navigator.NavigateTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

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