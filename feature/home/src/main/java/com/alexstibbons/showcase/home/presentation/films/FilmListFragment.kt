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
import com.alexstibbons.showcase.home.presentation.AddRemoveFave
import com.alexstibbons.showcase.home.presentation.BaseFragment
import com.alexstibbons.showcase.home.presentation.HomeViewModel
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class FilmListFragment : BaseFragment() {

    private val filmViewModel: FilmListViewModel by viewModel()

    companion object {
        fun newInstance() = FilmListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        infiniteScroll(recyclerLayoutManager) { filmViewModel.fetchFilms() }

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

    override fun populateRecycler(data: List<MediaModel>) {
        recyclerAdapter.addMedia(data)
        hideLoading()
    }


    private fun hideLoading() {

    }

    private fun showLoading() {
    }
}