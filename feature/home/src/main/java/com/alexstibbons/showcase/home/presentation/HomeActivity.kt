package com.alexstibbons.showcase.home.presentation

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexstibbons.showcase.ColoredSysBarActivity
import com.alexstibbons.showcase.home.R
import com.alexstibbons.showcase.home.injectFeature
import com.alexstibbons.showcase.home.presentation.recyclerView.RecyclerAdapter
import com.alexstibbons.showcase.showToast
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_home.activity_home_bottom_nav as bottomNav

internal class HomeActivity : ColoredSysBarActivity() {
    override val systemBarColor: Int
        get() = R.color.white

    private val homeVM: HomeViewModel by viewModel()

    private val recyclerAdapter: RecyclerAdapter by lazy {
        RecyclerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        injectFeature()

        initRecycler()

        bottomNav.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_films -> showToast("clicked films")
                R.id.menu_tv -> showToast("clicked tv")
                R.id.menu_fave -> showToast("clicked faves")
            }

            true
        }
        bottomNav.selectedItemId = R.id.menu_films
    }

    private fun initRecycler() {
        activity_home_recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            setHasFixedSize(true)
        }
    }
}