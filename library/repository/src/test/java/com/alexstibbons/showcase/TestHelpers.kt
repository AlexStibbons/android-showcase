package com.alexstibbons.showcase

import androidx.annotation.VisibleForTesting
import com.alexstibbons.showcase.database.FaveEntity
import com.alexstibbons.showcase.movieApi.model.FilmDetailsEntity
import com.alexstibbons.showcase.movieApi.model.FilmListItemEntity
import com.alexstibbons.showcase.movieApi.model.FilmListResponse
import com.alexstibbons.showcase.movieApi.model.VideoWrapper
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import org.junit.Assert


@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal inline fun Response<Failure, Any>.assertFailure(expected: Failure) {
    Assert.assertTrue(this.isFailure)
    val failure = (this as Response.Failure).failure
    Assert.assertEquals(expected::class.java, failure::class.java)
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal inline fun Response<Failure, Any>.assertSuccess(expected: Any) {
    Assert.assertTrue(this.isSuccess)
    val success = (this as Response.Success).success
    Assert.assertEquals(expected, success)
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockFaveEntity = FaveEntity(
    1,
    "title",
    "overview",
    "Drama",
    "image",
    0
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockFilmsDetails = FilmDetailsEntity(
    1,
    "title",
    "tagline",
    "overview",
    emptyList(),
    "tt435",
    "image",
    VideoWrapper(emptyList())
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockFilmItem = FilmListItemEntity(
    1,
    "title",
    "overview",
    "poster",
    "imdb",
    listOf(Genre.DRAMA.id)
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockFilmList = FilmListResponse(
    1,
    2,
    2,
    listOf(mockFilmItem)
)


