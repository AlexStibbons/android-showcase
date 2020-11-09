package com.alexstibbons.showcase.home

import androidx.annotation.VisibleForTesting
import com.alexstibbons.showcase.Genre
import com.alexstibbons.showcase.home.domain.MovieListDomain
import com.alexstibbons.showcase.home.domain.TvListDomain
import com.alexstibbons.showcase.movieApi.model.FilmListResponse
import com.alexstibbons.showcase.movieApi.model.GenreEntity
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.model.TvListResponse
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
internal val mockFilmListResponse = FilmListResponse(
    1,
    0,
    1,
    emptyList()
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockMovieListDomain = MovieListDomain(
    1,
    emptyList()
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockTvListResponse = TvListResponse(
    1,
    0,
    1,
    emptyList()
)

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
internal val mockTvListDomain = TvListDomain(
    1,
    emptyList()
)
