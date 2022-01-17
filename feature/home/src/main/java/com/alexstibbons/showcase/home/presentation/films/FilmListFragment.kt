package com.alexstibbons.showcase.home.presentation.films

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.presentation.*
import com.alexstibbons.showcase.home.presentation.AttachListener
import com.alexstibbons.showcase.home.presentation.BaseFragment
import com.alexstibbons.showcase.home.presentation.Search
import com.alexstibbons.showcase.search.NotifySearchSelected
import com.alexstibbons.showcase.search.SearchTerms
import com.alexstibbons.showcase.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FilmListFragment : BaseFragment(), NotifySearchSelected {

    private val filmViewModel: FilmListViewModel by viewModel()

    companion object {
        fun newInstance() = FilmListFragment()
    }

    override val search: Search = object: Search.SearchFilm {
        override fun open() {
            searchDialogue.newInstance(MediaType.FILM).show(childFragmentManager, "sklsksl")
        }

        override fun scrollToTop() {
            binding.fragmentRecycler.smoothScrollToPosition(0)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachListener = requireActivity() as? AttachListener
        attachListener?.attach(search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        infiniteScroll(recyclerLayoutManager) { filmViewModel.fetchFilms() }

        binding.fragmentClearSearch.setOnClickListener { clearSearch() }

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
            is FilmListViewModel.FilmListState.OnStartSearch -> prepareForSearch()
        }.exhaustive
    }

    private fun clearSearch() {
        showLoading()
        binding.fragmentClearSearch.isVisible = false
        recyclerAdapter.clearMedia()
        filmViewModel.onClearSearch()
    }

    private fun prepareForSearch() {
        showLoading()
        binding.fragmentClearSearch.isVisible = true
        recyclerAdapter.clearMedia()
    }

    private fun showError(message: Int) {
        val error = getString(message)
        hideLoading()
        requireActivity().showToast("error: $error")
    }

    override fun populateRecycler(data: List<MediaModel>) {
        recyclerAdapter.addMedia(data)
        hideLoading()
    }

    override fun onSearchTermsFilled(data: SearchTerms) {
        Log.e("in film search terms", "$data")
        filmViewModel.onStartSearch(data)
    }
}