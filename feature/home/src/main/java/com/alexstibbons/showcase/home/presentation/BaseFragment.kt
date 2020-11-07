package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexstibbons.showcase.MediaModel
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.navigator.NavigateTo
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

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

    protected val addRemoveFave = object: AddRemoveFave {
        override fun addFave(fave: MediaModel) = baseViewModel.addFave(fave)

        override fun removeFave(id: Int) = baseViewModel.removeFave(id)

    }

    protected val recyclerAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter(onMediaClick, addRemoveFave)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        injectFeature()

        initRecycler()

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
}