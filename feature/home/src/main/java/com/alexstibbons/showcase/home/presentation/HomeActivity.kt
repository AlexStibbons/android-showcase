package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.home.*
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.navigator.NavigateTo
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_home.activity_home_bottom_nav as bottomNav

internal class HomeActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int
        get() = R.color.white

    private val homeVM: HomeViewModel by viewModel()

    private val recyclerAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter(onMediaClick)
    }

    private val recyclerLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    private val onMediaClick: (Int, Int) -> Unit = {mediaType, mediaId -> startActivity(NavigateTo.mediaDetails(this, mediaType, mediaId))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        injectFeature()

        initRecycler()
        infiniteScroll(recyclerLayoutManager)

        homeVM.observeState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_films -> {
                    homeVM.setCurrentType(MediaType.FILM)
                    fetchMedia()
                }
                R.id.menu_tv -> {
                    homeVM.setCurrentType(MediaType.TV)
                    fetchMedia()
                }
                R.id.menu_fave -> {
                    homeVM.setCurrentType(MediaType.FAVE)
                    fetchMedia()
                }
            }

            true
        }
        bottomNav.selectedItemId = R.id.menu_films
    }

    private fun renderState(state: HomeViewModel.ViewState) {
        when (state) {
            is HomeViewModel.ViewState.Loading -> showLoading()
            is HomeViewModel.ViewState.Error -> showError(state.message)
            is HomeViewModel.ViewState.Success -> populateRecycler(state.data.data)
        }.exhaustive
    }

    private fun initRecycler() {
        activity_home_recycler.apply {
            adapter = recyclerAdapter
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun showError(message: Int) {
        val error = getString(message)
        hideLoading()
        showToast("error: $error")
    }

    private fun showLoading() {

    }

    private fun populateRecycler(data: List<MediaModel>) {
        recyclerAdapter.addMedia(data)
        hideLoading()
    }

    private fun hideLoading() {

    }

    private fun fetchMedia() {
        recyclerAdapter.clearList()
        homeVM.resetCurrentPage()
        homeVM.fetchMediaList()
    }

    private fun infiniteScroll(layoutManager: LinearLayoutManager) {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItem = layoutManager.childCount
                val totalItems = layoutManager.itemCount
                val scrollOutItems = layoutManager.findFirstVisibleItemPosition()

                if (currentItem + scrollOutItems == totalItems - 5) {
                    homeVM.fetchMediaList()
                }
            }
        }

        activity_home_recycler.addOnScrollListener(listener)
    }
}