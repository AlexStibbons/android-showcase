package com.alexstibbons.showcase.home.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexstibbons.showcase.MediaType
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.home.presentation.faves.FaveListFragment
import com.alexstibbons.showcase.home.presentation.films.FilmListFragment
import com.alexstibbons.showcase.home.presentation.tv.TvListFragment

internal class HomeViewPagerAdapter(host: AppCompatActivity) : FragmentStateAdapter(host) {

    private val typeList = listOf(MediaType.FILM, MediaType.TV, MediaType.FAVE)

    override fun getItemCount(): Int = typeList.size

    override fun createFragment(position: Int): Fragment = when (val type = typeList[position]) {
        MediaType.FILM -> FilmListFragment.newInstance()
        MediaType.TV -> TvListFragment.newInstance()
        MediaType.FAVE -> FaveListFragment.newInstance()
    }.exhaustive as Fragment

}