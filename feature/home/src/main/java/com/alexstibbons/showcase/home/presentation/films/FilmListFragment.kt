package com.alexstibbons.showcase.home.presentation.films

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.home.presentation.HomeViewModel
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class FilmListFragment : Fragment(R.layout.fragment_base) {

    private val baseViewModel: HomeViewModel by sharedViewModel()
    private val filmViewModel: FilmListViewModel by viewModel()

    private val recyclerLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(requireActivity()) }

    private val onMediaClick: (Int, Int) -> Unit = {mediaType, mediaId -> startActivity(NavigateTo.mediaDetails(requireActivity(), mediaType, mediaId))}

    private val recyclerAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter(onMediaClick)
    }

    companion object {
        fun newInstance() = FilmListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFeature()

        initRecycler()
        infiniteScroll(recyclerLayoutManager)

        temp_text.isVisible = false

        filmViewModel.observeFilms().observe(viewLifecycleOwner, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })
    }

    private fun renderState(state: FilmListViewModel.FilmListState) {
        when (state) {
            is FilmListViewModel.FilmListState.Loading -> showLoading()
            is FilmListViewModel.FilmListState.Error -> showError(state.message)
            is FilmListViewModel.FilmListState.Success -> populateRecycler(state.data.data)
        }.exhaustive
    }

    private fun showError(message: Int) {
        val error = getString(message)
        hideLoading()
        requireActivity().showToast("error: $error")
    }

    private fun initRecycler() {
        fragment_recycler.apply {
            adapter = recyclerAdapter
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun populateRecycler(data: List<MediaModel>) {
        recyclerAdapter.addMedia(data)
        hideLoading()
    }

    private fun infiniteScroll(layoutManager: LinearLayoutManager) {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItem = layoutManager.childCount
                val totalItems = layoutManager.itemCount
                val scrollOutItems = layoutManager.findFirstVisibleItemPosition()

                if (currentItem + scrollOutItems == totalItems - 5) {
                    filmViewModel.fetchFilms()
                }
            }
        }

        fragment_recycler.addOnScrollListener(listener)
    }

    private fun hideLoading() {

    }

    private fun showLoading() {

    }
}