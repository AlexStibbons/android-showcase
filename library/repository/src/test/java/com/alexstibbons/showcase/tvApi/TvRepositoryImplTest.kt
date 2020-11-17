package com.alexstibbons.showcase.tvApi

import com.alexstibbons.showcase.*
import com.alexstibbons.showcase.assertFailure
import com.alexstibbons.showcase.assertSuccess
import com.alexstibbons.showcase.mockFilmList
import com.alexstibbons.showcase.mockTvDetails
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

internal class TvRepositoryImplTest {

    private val mockApi: TvApi = mockk(relaxed = true)

    private lateinit var repo: TvRepositoryImpl

    @Before
    fun setUp() {
        clearAllMocks()
        repo = TvRepositoryImpl(mockApi)
    }

    @Test
    fun `when api returns an empty success for get tv, repo should return a no such media error`() {
        coEvery { mockApi.getTvShow(id = 1, apiKey = any()) } returns  Response.success(null)

        val result = runBlocking { repo.getTvShowDetails(1) }

        result.assertFailure(MediaFailure.NoSuchMedia)
        coVerify { mockApi.getTvShow(id = 1, apiKey = any()) }
        confirmVerified(mockApi)
    }

    @Test
    fun `when api returns a server error for get movie, repo should return the same`() {
        val mockBody: ResponseBody = mockk()
        coEvery { mockApi.getTvShow(id = 1, apiKey = any()) } answers { Response.error(500, mockBody)}

        val result = runBlocking { repo.getTvShowDetails(1) }

        result.assertFailure(Failure.ServerError)
        coVerify { mockApi.getTvShow(id = 1, apiKey = any()) }
        confirmVerified(mockApi)
    }

    @Test
    fun `when api returns a success for get movie, repo should return the same`() {
        coEvery { mockApi.getTvShow(id = 1, apiKey = any()) } returns Response.success(mockTvDetails)

        val result = runBlocking { repo.getTvShowDetails(1) }

        result.assertSuccess(mockTvDetails)
        coVerify { mockApi.getTvShow(id = 1, apiKey = any()) }
        confirmVerified(mockApi)
    }

    @Test
    fun `when there is no search terms, and repo returns films on get popular films, return the same`() {
        coEvery { mockApi.getPopularTvShows(page = 1, apiKey = any()) } returns Response.success(mockTvList)

        val result = runBlocking { repo.getPopular(1, null) }

        result.assertSuccess(mockTvList)
        coVerifyAll {
            mockApi.getPopularTvShows(page = 1, apiKey = any())
            mockApi.searchByGenre(page = any(), apiKey = any(), genres = any()) wasNot Called
            mockApi.searchByTitle(apiKey = any(), page = any(), query = any()) wasNot Called
        }
        confirmVerified(mockApi)
    }

    @Test
    fun `when there are search terms, and there is a title query, and repo returns films on get films, return the same`() {
        val mockSearchTermsRepo = SearchTermsRepo(MediaType.FILM, "title", emptyList())
        coEvery { mockApi.searchByTitle(page = 1, apiKey = any(), query = mockSearchTermsRepo.title) } returns Response.success(mockTvList)

        val result = runBlocking { repo.getPopular(1, mockSearchTermsRepo) }

        result.assertSuccess(mockTvList)
        coVerifyAll {
            mockApi.getPopularTvShows(page = 1, apiKey = any()) wasNot Called
            mockApi.searchByGenre(page = any(), apiKey = any(), genres = any()) wasNot Called
            mockApi.searchByTitle(apiKey = any(), page = any(), query = mockSearchTermsRepo.title)
        }
        confirmVerified(mockApi)
    }

    @Test
    fun `when there are search terms, and repo returns films on get films, return the same`() {
        val mockSearchTermsRepo = SearchTermsRepo(MediaType.FILM, "", listOf(Genre.DRAMA))
        coEvery { mockApi.searchByGenre(page = 1, apiKey = any(), genres = any()) } returns Response.success(mockTvList)

        val result = runBlocking { repo.getPopular(1, mockSearchTermsRepo) }

        result.assertSuccess(mockTvList)
        coVerifyAll {
            mockApi.getPopularTvShows(page = 1, apiKey = any()) wasNot Called
            mockApi.searchByGenre(page = any(), apiKey = any(), genres = any())
            mockApi.searchByTitle(apiKey = any(), page = any(), query = any()) wasNot Called
        }
        confirmVerified(mockApi)
    }
}