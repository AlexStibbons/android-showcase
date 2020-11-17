package com.alexstibbons.showcase.movieApi

import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.assertFailure
import com.alexstibbons.showcase.assertSuccess
import com.alexstibbons.showcase.mockFilmsDetails
import com.alexstibbons.showcase.responses.Failure
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

internal class MovieRepositoryImplTest {

    private val mockApi: MovieApi = mockk(relaxed = true)
    private val mockSearchTermsRepo = SearchTermsRepo(MediaType.FILM, "", listOf(Genre.DRAMA) )

    private lateinit var repo: MovieRepositoryImpl

    @Before
    fun setUp() {
        clearAllMocks()
        repo = MovieRepositoryImpl(mockApi)
    }

    @Test
    fun `when api returns an empty success for get movie, repo should return a no such media error`() {
        coEvery { mockApi.getMovie(1, any()) } returns  Response.success(null)

        val result = runBlocking { repo.getMovie(1) }

        result.assertFailure(MediaFailure.NoSuchMedia)
        coVerify { mockApi.getMovie(1, any()) }
        confirmVerified(mockApi)
    }

    @Test
    fun `when api returns a server error for get movie, repo should return the same`() {
        val mockBody: ResponseBody = mockk()
        coEvery { mockApi.getMovie(1, any()) } answers { Response.error(500, mockBody)}

        val result = runBlocking { repo.getMovie(1) }

        result.assertFailure(Failure.ServerError)
        coVerify { mockApi.getMovie(1, any()) }
        confirmVerified(mockApi)
    }

    @Test
    fun `when api returns a success for get movie, repo should return the same`() {
        coEvery { mockApi.getMovie(1, any()) } returns Response.success(mockFilmsDetails)

        val result = runBlocking { repo.getMovie(1) }

        result.assertSuccess(mockFilmsDetails)
        coVerify { mockApi.getMovie(1, any()) }
        confirmVerified(mockApi)
    }

    @Test
    fun `when there is no search terms, and repo returns films on get popular films, return the same`() {
        coEvery { mockApi.getPopularMovies(page = 1, apiKey = any()) } returns Response.success(mockFilmList)

        val result = runBlocking { repo.getFilms(1, null) }

        result.assertSuccess(mockFilmList)
        coVerifyAll {
            mockApi.getPopularMovies(page = 1, apiKey = any())
            mockApi.searchByGenre(page = any(), apiKey = any(), genres = any()) wasNot Called
            mockApi.searchByTitle(apiKey = any(), page = any(), query = any()) wasNot Called
        }
        confirmVerified(mockApi)
    }

    @Test
    fun `when there are search terms, and there is a title query, and repo returns films on get films, return the same`() {
        val mockSearchTermsRepo = SearchTermsRepo(MediaType.FILM, "title", emptyList())
        coEvery { mockApi.searchByTitle(page = 1, apiKey = any(), query = mockSearchTermsRepo.title) } returns Response.success(mockFilmList)

        val result = runBlocking { repo.getFilms(1, mockSearchTermsRepo) }

        result.assertSuccess(mockFilmList)
        coVerifyAll {
            mockApi.getPopularMovies(page = 1, apiKey = any()) wasNot Called
            mockApi.searchByGenre(page = any(), apiKey = any(), genres = any()) wasNot Called
            mockApi.searchByTitle(apiKey = any(), page = any(), query = mockSearchTermsRepo.title)
        }
        confirmVerified(mockApi)
    }

    @Test
    fun `when there are search terms, and repo returns films on get films, return the same`() {
        val mockSearchTermsRepo = SearchTermsRepo(MediaType.FILM, "", listOf(Genre.DRAMA))
        coEvery { mockApi.searchByGenre(page = 1, apiKey = any(), genres = any()) } returns Response.success(mockFilmList)

        val result = runBlocking { repo.getFilms(1, mockSearchTermsRepo) }

        result.assertSuccess(mockFilmList)
        coVerifyAll {
            mockApi.getPopularMovies(page = 1, apiKey = any()) wasNot Called
            mockApi.searchByGenre(page = any(), apiKey = any(), genres = any())
            mockApi.searchByTitle(apiKey = any(), page = any(), query = any()) wasNot Called
        }
        confirmVerified(mockApi)
    }
}