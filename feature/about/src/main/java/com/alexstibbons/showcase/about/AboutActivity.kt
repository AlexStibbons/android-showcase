package com.alexstibbons.showcase.about

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import com.alexstibbons.showcase.ColoredSysBarActivity

internal class AboutActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int = R.color.transparent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setTransparentSystemBar()
    }

    private fun setTransparentSystemBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}