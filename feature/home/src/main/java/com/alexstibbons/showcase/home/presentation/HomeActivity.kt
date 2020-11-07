package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import android.util.Log
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.argumentOrThrow
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.FAVE_IDS_ARRAY
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlinx.android.synthetic.main.activity_home.activity_home_search as searchIcon

internal interface AddRemoveFave {
    fun addFave(fave: MediaModel)
    fun removeFave(id: Int)
}

internal class HomeActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int = R.color.white

    private val cachedFaveIds: ArrayList<Int> by argumentOrThrow(FAVE_IDS_ARRAY)

    private val fragmentAdapter by lazy { HomeViewPagerAdapter(this) }

    private val baseViewModel: HomeViewModel by viewModel { parametersOf(cachedFaveIds) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.e("in home", "$cachedFaveIds")
        injectFeature()

        val faves = baseViewModel.cachedIds()
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
            when (activity_home_viewPager.currentItem) {
                0 -> showToast("search for films")
                1 -> showToast("search for tv")
                2 -> showToast("search for faves")
            }
        }

        activity_home_about.setOnClickListener { showToast("Open about page") }
    }
}