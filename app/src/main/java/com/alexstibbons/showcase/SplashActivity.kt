package com.alexstibbons.showcase

import android.os.Bundle

class SplashActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int
        get() = R.color.colorAccent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

    }
}