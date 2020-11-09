package com.alexstibbons.showcase.details

import androidx.annotation.VisibleForTesting
import com.alexstibbons.showcase.Genre
import com.alexstibbons.showcase.details.domain.MediaDetailsModel
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.GenreEntity
import com.alexstibbons.showcase.movieApi.model.VideoWrapper
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.model.TvShowDetailsEntity
import org.junit.Assert.*

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal inline fun Response<Failure, Any>.assertFailure(expected: Failure) {
    assertTrue(this.isFailure)
    val failure = (this as Response.Failure).failure
    assertEquals(expected::class.java, failure::class.java)
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal inline fun Response<Failure, Any>.assertSuccess(expected: Any) {
    assertTrue(this.isSuccess)
    val success = (this as Response.Success).success
    assertEquals(expected, success)
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockGenreEntityList = listOf(GenreEntity(18, "Drama"))

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockGenreEnumList = listOf(Genre.DRAMA)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockFilmEntity = FilmDetailsEntity(
    123,
    "Title",
    "Tagline",
    "Overview",
    mockGenreEntityList,
    "imdb_id",
    "poster_path",
    VideoWrapper(emptyList())
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockFilmDetails = MediaDetailsModel.FilmDetails(
    123,
    "Title",
    "Tagline",
    "Overview",
    "poster_path",
    "https://www.imdb.com/title/imdb_id",
    null,
    mockGenreEnumList
)


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockTvEntity = TvShowDetailsEntity(
    123,
    "Title",
    "Overview",
    mockGenreEntityList,
    "image",
    VideoWrapper(emptyList())
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockTvDetails = MediaDetailsModel.TvDetails(
    123,
    "Title",
    "Overview",
    "image",
    null,
    mockGenreEnumList
)



