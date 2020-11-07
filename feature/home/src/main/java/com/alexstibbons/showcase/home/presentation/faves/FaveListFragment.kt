package com.alexstibbons.showcase.home.presentation.faves

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.presentation.BaseFragment
import com.alexstibbons.showcase.home.presentation.recyclerView.FaveRecyclerAdapter
import com.alexstibbons.showcase.home.presentation.recyclerView.ItemViewHolder
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapterBase
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.viewModel

internal class FaveListFragment : BaseFragment() {

    private val faveVm: FaveListViewModel by viewModel()

    override val recyclerAdapter: RecyclerAdapterBase by lazy {
        FaveRecyclerAdapter(onMediaClick, addRemoveFave, isMediaInFaveCache)
    }

    override val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if ("fave_change" == intent?.action) {
                baseViewModel.updateCachedFaves()
                faveVm.updateFaves()
            }
        }
    }

    companion object {
        fun newInstance() = FaveListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        faveVm.observeState().observe(viewLifecycleOwner, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

    }

    private fun renderState(state: FaveListViewModel.ViewState) {
        when (state) {
            FaveListViewModel.ViewState.Loading -> showLoading()
            is FaveListViewModel.ViewState.Success -> populateRecycler(state.data.data)
            is FaveListViewModel.ViewState.Error ->  showError(state.message)
        }.exhaustive
    }

    override fun populateRecycler(data: List<MediaModel>) {
        recyclerAdapter.addMedia(data)
        hideLoading()
    }

    private fun hideLoading() {

    }

    private fun showLoading() {
    }

    private fun showError(message: Int) {
        val error = getString(message)
        hideLoading()
        requireActivity().showToast("error: $error")
    }
}