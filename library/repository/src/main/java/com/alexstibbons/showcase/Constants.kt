package com.alexstibbons.showcase

const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

const val BASE_YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="

enum class Genre(val id: Int, val title: String) {
    ACTION(28, "Action"),
    ADVENTURE(12, "Adventure"),
    ANIMATION(16, "Animation"),
    COMEDY(35, "Comedy"),
    CRIME(80, "Crime"),
    DOCUMENTARY(99, "Documentary"),
    DRAMA(18, "Drama"),
    FAMILY(10751, "Family"),
    FANTASY(14, "Fantasy"),
    HISTORY(36, "History"),
    HORROR(27, "Horror"),
    MUSIC(10402, "Music"),
    MYSTERY(9648, "Mystery"),
    ROMANCE(10749, "Romance"),
    SCIFI(878, "Science Fiction"),
    TV_MOVIE(10770, "TV Movie"),
    THRILLER(53, "Thriller"),
    WAR(10752, "War"),
    WESTERN(37, "Western"),
    ACTION_ADVENTURE(10759, "Action & Adventure"),
    KIDS(10762, "Kids"),
    NEWS(10763, "News"),
    REALITY(10764, "Reality"),
    SFF(10765, "Sci-Fi & Fantasy"),
    SOAP(10766, "Soap"),
    TALK(10767, "Talk"),
    POLITICS(10768, "War & Politics"),
    UNKNOWN(-1, "Unknown");

    companion object {
        fun from(id: Int) = values().find { it.id == id } ?: UNKNOWN
        fun from(title: String) = values().find { it.title == title } ?: UNKNOWN
    }
}