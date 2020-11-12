package com.alexstibbons.showcase.home.presentation.tv

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.home.presentation.*
import com.alexstibbons.showcase.home.presentation.AttachListener
import com.alexstibbons.showcase.home.presentation.BaseFragment
import com.alexstibbons.showcase.home.presentation.Search
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.search.NotifySearchSelected
import com.alexstibbons.showcase.search.SearchTerms
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


internal interface SearchTv: Search {
    fun open()
    fun scrollToTop()
}


internal class TvListFragment : BaseFragment(), NotifySearchSelected {

    private val tvViewModel: TvListViewModel by viewModel()

    companion object {
        fun newInstance() = TvListFragment()
    }

    override val search: Search = object : SearchTv {
        override fun open() {
            searchDialogue.newInstance(MediaType.TV).show(childFragmentManager, "Tv search")
        }

        override fun scrollToTop() {
            fragment_recycler.smoothScrollToPosition(0)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachListener = requireActivity() as? AttachListener
        attachListener?.attach(search)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infiniteScroll(recyclerLayoutManager) { tvViewModel.fetchTv() }

        fragment_clear_search.setOnClickListener { clearSearch() }

        tvViewModel.observeFilms().observe(viewLifecycleOwner, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

    }

    private fun renderState(state: TvListViewModel.TvListState) {
        when (state) {
            is TvListViewModel.TvListState.Loading -> showLoading()
            is TvListViewModel.TvListState.Error -> showError(state.message)
            is TvListViewModel.TvListState.Success -> populateRecycler(state.data.data)
            TvListViewModel.TvListState.OnStartSearch -> prepareForSearch()
        }.exhaustive
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
        Log.e("in tv search terms", "$data")
        tvViewModel.onStartSearch(data)
    }

    private fun clearSearch() {
        showLoading()
        fragment_clear_search.isVisible = false
        recyclerAdapter.clearMedia()
        tvViewModel.onClearSearch()
    }

    private fun prepareForSearch() {
        showLoading()
        fragment_clear_search.isVisible = true
        recyclerAdapter.clearMedia()
    }
}