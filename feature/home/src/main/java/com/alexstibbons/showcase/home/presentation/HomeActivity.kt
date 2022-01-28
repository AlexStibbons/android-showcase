package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.databinding.ActivityHomeBinding
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.navigator.NavigateTo.BundleKeys.FAVE_IDS_ARRAY
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal interface AddRemoveFave {
    fun addFave(fave: MediaModel)
    fun removeFave(id: Int)
}

internal class HomeActivity : ColoredSysBarActivity(), AttachListener {
    override val systemBarColor: Int = R.color.white

    private lateinit var binding: ActivityHomeBinding

    private val cachedFaveIds: ArrayList<Int> by argumentOrThrow(FAVE_IDS_ARRAY)

    private val fragmentAdapter by lazy { HomeViewPagerAdapter(this) }

    private val baseViewModel: HomeViewModel by viewModel { parametersOf(cachedFaveIds) }

    private var searchFilm: Search.SearchFilm? = null
    private var searchTv: Search.SearchTv? = null
    private var searchFaves: Search.SearchFaves? = null

    private val onPageSelected by lazy {
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.activityHomeBottomNav.selectedItemId = R.id.menu_films
                    1 -> binding.activityHomeBottomNav.selectedItemId = R.id.menu_tv
                    2 -> binding.activityHomeBottomNav.selectedItemId = R.id.menu_fave
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        val faves = baseViewModel.cachedIds()

        with(binding) {
            activityHomeViewPager.adapter = fragmentAdapter
            activityHomeViewPager.registerOnPageChangeCallback(onPageSelected)

            activityHomeBottomNav.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_films -> activityHomeViewPager.setCurrentItem(0, false)
                    R.id.menu_tv -> activityHomeViewPager.setCurrentItem(1, false)
                    R.id.menu_fave -> activityHomeViewPager.setCurrentItem(2, false)
                }

                true
            }
            activityHomeBottomNav.selectedItemId = R.id.menu_films

            activityHomeSearch.setOnClickListener {
                when (activityHomeViewPager.currentItem) {
                    0 -> searchFilm?.open()
                    1 -> searchTv?.open()
                    2 -> searchFaves?.open()
                }
            }

            activityHomeAbout.setOnClickListener { startActivity(NavigateTo.login(this@HomeActivity)) }//startActivity(NavigateTo.about(this@HomeActivity)) }

            activityHomeUser.setOnClickListener { baseViewModel.getUserId() }


            activityHomeBottomNav.setOnNavigationItemReselectedListener { item ->
                when (item.itemId) {
                    R.id.menu_films -> searchFilm?.scrollToTop()
                    R.id.menu_tv -> searchTv?.scrollToTop()
                    R.id.menu_fave -> searchFaves?.scrollToTop()
                }
            }
        }
    }

    override fun attach(listener: Search) = when (listener) {
        is Search.SearchFilm -> searchFilm = listener
        is Search.SearchTv -> searchTv = listener
        is Search.SearchFaves -> searchFaves = listener
    }.exhaustive

    override fun onDestroy() {
        binding.activityHomeViewPager.unregisterOnPageChangeCallback(onPageSelected)
        super.onDestroy()
    }
}