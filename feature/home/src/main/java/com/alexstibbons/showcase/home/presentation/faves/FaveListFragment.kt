package com.alexstibbons.showcase.home.presentation.faves

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.presentation.AttachListener
import com.alexstibbons.showcase.home.presentation.tv.Search
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.fragment_base.*

internal interface SearchFaves: Search {
    fun open()
}

internal class FaveListFragment : Fragment(R.layout.fragment_base) {

    private val faveSearch = object: SearchFaves {
        override fun open() {
            requireActivity().showToast("fave fave fave")
        }
    }

    private var attachListener: AttachListener? = null

    companion object {
        fun newInstance() = FaveListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        attachListener = requireActivity() as? AttachListener
        attachListener?.attach(faveSearch)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        temp_text.text = "FAVE LIST"
    }
}