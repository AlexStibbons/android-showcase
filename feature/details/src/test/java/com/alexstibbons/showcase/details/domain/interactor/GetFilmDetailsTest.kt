package com.alexstibbons.showcase.details.domain.interactor

import com.alexstibbons.showcase.details.assertFailure
import com.alexstibbons.showcase.details.assertSuccess
import com.alexstibbons.showcase.details.mockFilmDetails
import com.alexstibbons.showcase.details.mockFilmEntity
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.movieApi.MovieRepository
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

internal class GetFilmDetailsTest {

    private val mockNetwork: NetworkHandler = mockk()
    private val mockRepo: MovieRepository = mockk()

    private lateinit var useCase: GetFilmDetails

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = GetFilmDetails(mockNetwork, mockRepo)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when there's no params, use case should throw an exception`() {
        runBlocking { useCase.run(null) }

        coVerifyAll {
            mockNetwork wasNot Called
            mockRepo wasNot Called
        }
        confirmVerified(mockNetwork, mockRepo)
    }

    @Test
    fun `when there's no network, use case should return no connection error`() {
        every { mockNetwork.isConnected } returns false

        val result = runBlocking { useCase.run(123) }

        result.assertFailure(Failure.NetworkConnection)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo wasNot Called
        }
        confirmVerified(mockNetwork, mockRepo)
    }

    @Test
    fun `when repo returns an error, use case should return the same error`() {
        every { mockNetwork.isConnected } returns true
        coEvery { mockRepo.getMovie(123) } returns Response.failure(MediaFailure.NoSuchMedia)

        val result = runBlocking { useCase.run(123) }

        result.assertFailure(MediaFailure.NoSuchMedia)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo.getMovie(123)
        }
        confirmVerified(mockNetwork, mockRepo)
    }

    @Test
    fun `when repo returns a film, use case should return success`() {
        every { mockNetwork.isConnected } returns true
        coEvery { mockRepo.getMovie(123) } returns Response.success(mockFilmEntity)

        val result = runBlocking { useCase.run(123) }

        result.assertSuccess(mockFilmDetails)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo.getMovie(123)
        }
        confirmVerified(mockNetwork, mockRepo)
    }
}