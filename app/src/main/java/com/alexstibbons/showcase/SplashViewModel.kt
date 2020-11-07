package com.alexstibbons.showcase

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexstibbons.showcase.database.domain.GetFaveIds
import com.alexstibbons.showcase.navigator.NavigateTo
import com.alexstibbons.showcase.responses.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getFaveIds: GetFaveIds
): ViewModel() {

    private var _screen = MutableLiveData<Screen>()
    fun observeScreen(): LiveData<Screen> = _screen

    init {
        showSplash()
    }

    private fun showSplash() = viewModelScope.launch(Dispatchers.Main) {
        getFaveIds { response ->
            if (response is Response.Success) {
                val list = ArrayList<Int>()
                response.success.toIntArray().toCollection(list)
                gotoHome(list)
            }
        }
    }

    private fun gotoHome(faves: ArrayList<Int>) = viewModelScope.launch(Dispatchers.Main) {
        delay(1000L)
        _screen.value = Screen.Home(faves)
    }

    override fun onCleared() {
        getFaveIds.cancel()
        super.onCleared()
    }


    sealed class Screen {
        abstract fun intent(context: Context): Intent
        abstract val faves: ArrayList<Int>

        class Home(faves: ArrayList<Int>): Screen() {
            override val faves: ArrayList<Int> = faves
            override fun intent(context: Context): Intent = NavigateTo.movieList(context, faves)

        }
    }
}