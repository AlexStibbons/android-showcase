package com.alexstibbons.showcase.home.presentation


internal sealed interface Search {
    fun open()
    fun scrollToTop()

    interface SearchFilm: Search
    interface SearchFaves : Search
    interface SearchTv : Search

}

internal interface AttachListener {
    fun attach(listener: Search)
}
