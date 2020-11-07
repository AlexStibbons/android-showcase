package com.alexstibbons.showcase.home.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.navigator.NavigateTo
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

const val BROADCAST_CHANGE = "fave_change"

internal abstract class BaseFragment : Fragment(R.layout.fragment_base) {

    protected val baseViewModel: HomeViewModel by sharedViewModel()

    protected val recyclerLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(
            requireActivity()
        )
    }

    protected val onMediaClick: (Int, Int) -> Unit = { mediaType, mediaId ->
        startActivity(
            NavigateTo.mediaDetails(
                requireActivity(),
                mediaType,
                mediaId
            )
        )
    }

    protected val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if ("fave_change" == intent?.action) {
                baseViewModel.updateCachedFaves()
            }
        }
    }

    protected val addRemoveFave = object: AddRemoveFave {
        override fun addFave(fave: MediaModel) {
            baseViewModel.addFave(fave)
            requireActivity().sendBroadcast(Intent(BROADCAST_CHANGE))
        }

        override fun removeFave(id: Int) {
            baseViewModel.removeFave(id)
            requireActivity().sendBroadcast(Intent(BROADCAST_CHANGE))
        }

    }

    protected val recyclerAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter(onMediaClick, addRemoveFave, isMediaInFaveCache)
    }

    protected val isMediaInFaveCache: (Int) -> Boolean = { mediaId ->
        baseViewModel.cachedIds().contains(mediaId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFeature()

        requireActivity().registerReceiver(broadcastReceiver, IntentFilter(BROADCAST_CHANGE))
        initRecycler()

        baseViewModel.observeState().observe(viewLifecycleOwner, Observer { state ->
            state ?: return@Observer

            if (state is HomeViewModel.ViewState.NewFaves) recyclerAdapter.updateFaves(state.data)
        })

    }

    private fun initRecycler() {
        fragment_recycler.apply {
            adapter = recyclerAdapter
            layoutManager = recyclerLayoutManager
            setHasFixedSize(true)
        }
    }

    protected fun infiniteScroll(layoutManager: LinearLayoutManager, action: () -> Unit) {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val currentItem = layoutManager.childCount
                val totalItems = layoutManager.itemCount
                val scrollOutItems = layoutManager.findFirstVisibleItemPosition()

                if (currentItem + scrollOutItems == totalItems - 5) {
                    action.invoke()
                }
            }
        }

        fragment_recycler.addOnScrollListener(listener)
    }

    abstract fun populateRecycler(data: List<MediaModel>)

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(broadcastReceiver)
    }
}