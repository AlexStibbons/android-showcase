package com.alexstibbons.showcase.home

import android.os.Bundle
import com.alexstibbons.showcase.ColoredSysBarActivity

internal class HomeActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int
        get() = R.color.colorPrimaryDark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}