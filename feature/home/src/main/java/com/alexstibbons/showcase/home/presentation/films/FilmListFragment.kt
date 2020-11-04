package com.alexstibbons.showcase.home.presentation.films

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.presentation.HomeViewModel
import kotlinx.android.synthetic.main.fragment_base.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

internal class FilmListFragment : Fragment(R.layout.fragment_base) {

    companion object {
        fun newInstance() = FilmListFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        temp_text.text = "FILM LIST"
    }
}