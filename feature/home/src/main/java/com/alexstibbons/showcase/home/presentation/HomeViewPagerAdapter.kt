package com.alexstibbons.showcase.home.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexstibbons.showcase.home.presentation.faves.FaveListFragment
import com.alexstibbons.showcase.home.presentation.films.FilmListFragment
import com.alexstibbons.showcase.home.presentation.tv.TvListFragment

internal class HomeViewPagerAdapter(host: AppCompatActivity) : FragmentStateAdapter(host) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
           1 -> FilmListFragment.newInstance()
           2 -> TvListFragment.newInstance()
           3 -> FaveListFragment.newInstance()
           else -> error("No such fragment")
       }

}