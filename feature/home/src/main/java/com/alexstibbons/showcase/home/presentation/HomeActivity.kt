package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.activity_home.activity_home_search as searchIcon

internal class HomeActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int = R.color.white

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        injectFeature()

        searchIcon.setOnClickListener {
            showToast("Search bottom dialogue pops up here now")
        }
    }
}