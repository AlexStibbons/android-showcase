package com.alexstibbons.showcase.home.presentation.films

import androidx.fragment.app.Fragment
import com.alexstibbons.showcase.home.presentation.HomeViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

internal class FilmListFragment : Fragment() {

    private val baseViewModel: HomeViewModel by sharedViewModel()

    companion object {
        fun newInstance() = FilmListFragment()
    }
}