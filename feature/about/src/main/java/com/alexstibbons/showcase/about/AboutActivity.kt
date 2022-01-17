package com.alexstibbons.showcase.about

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.about.databinding.ActivityAboutBinding

internal class AboutActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int = R.color.transparent
    private lateinit var aboutBinding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aboutBinding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(aboutBinding.root)

        setTransparentSystemBar()

        with(aboutBinding) {
            aboutFirstParag.movementMethod = LinkMovementMethod.getInstance()
            aboutBack.setOnClickListener { super.onBackPressed() }
        }

    }

    private fun setTransparentSystemBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}