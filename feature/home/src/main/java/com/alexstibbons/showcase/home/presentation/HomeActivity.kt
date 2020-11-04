package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_temp.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_home.activity_home_search as searchIcon

internal class HomeActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int = R.color.white

    private val fragmentAdapter by lazy { HomeViewPagerAdapter(this) }

    private val baseViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        injectFeature()

        activity_home_viewPager.adapter = fragmentAdapter

        activity_home_bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_films -> activity_home_viewPager.setCurrentItem(0, false)
                R.id.menu_tv -> activity_home_viewPager.setCurrentItem(1, false)
                R.id.menu_fave -> activity_home_viewPager.setCurrentItem(2, false)
            }

            true
        }
        activity_home_bottom_nav.selectedItemId = R.id.menu_films

        searchIcon.setOnClickListener {
            showToast("Search bottom dialogue pops up here now")
        }
    }
}