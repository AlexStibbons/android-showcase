package com.alexstibbons.showcase.home.presentation.faves

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexstibbons.showcase.home.R
import kotlinx.android.synthetic.main.fragment_base.*

internal class FaveListFragment : Fragment(R.layout.fragment_base) {

    companion object {
        fun newInstance() = FaveListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        temp_text.text = "FAVE LIST"
    }
}