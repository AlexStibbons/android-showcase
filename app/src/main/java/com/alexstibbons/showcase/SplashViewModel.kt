package com.alexstibbons.showcase

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstibbons.showcase.navigator.NavigateTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private var _screen = MutableLiveData<Screen>()
    fun observeScreen(): LiveData<Screen> = _screen

    init {
        showSplash()
    }

    private fun showSplash() = viewModelScope.launch(Dispatchers.Main) {
        delay(1000L)
        _screen.value = Screen.Home
    }


    sealed class Screen {
        abstract fun intent(context: Context): Intent

        object Home: Screen() {
            override fun intent(context: Context): Intent = NavigateTo.movieList(context)
        }
    }
}