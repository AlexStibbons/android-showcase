package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.home.*
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
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

    private val onMediaClick: (String) -> Unit = {id -> showToast("clicked on an item: $id")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        injectFeature()

        initRecycler()

        homeVM.observeState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_films -> fetchMedia(MediaType.FILM)
                R.id.menu_tv -> fetchMedia(MediaType.TV)
                R.id.menu_fave -> fetchMedia(MediaType.FAVE)
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
            layoutManager = LinearLayoutManager(this@HomeActivity)
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

    private fun fetchMedia(type: MediaType) {
        recyclerAdapter.clearList()
        homeVM.fetchMediaList(type)
    }
}