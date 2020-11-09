package com.alexstibbons.showcase.details.domain.interactor

import com.alexstibbons.showcase.details.*
import com.alexstibbons.showcase.details.assertFailure
import com.alexstibbons.showcase.details.assertSuccess
import com.alexstibbons.showcase.details.mockTvEntity
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.TvRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

internal class GetTvDetailsTest {

    private val mockNetwork: NetworkHandler = mockk()
    private val mockRepo: TvRepository = mockk()

    private lateinit var useCase: GetTvDetails

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = GetTvDetails(mockNetwork, mockRepo)
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
        coEvery { mockRepo.getTvShowDetails(123) } returns Response.failure(MediaFailure.NoSuchMedia)

        val result = runBlocking { useCase.run(123) }

        result.assertFailure(MediaFailure.NoSuchMedia)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo.getTvShowDetails(123)
        }
        confirmVerified(mockNetwork, mockRepo)
    }

    @Test
    fun `when repo returns a tv show, use case should return success`() {
        every { mockNetwork.isConnected } returns true
        coEvery { mockRepo.getTvShowDetails(123) } returns Response.success(mockTvEntity)

        val result = runBlocking { useCase.run(123) }

        result.assertSuccess(mockTvDetails)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo.getTvShowDetails(123)
        }
        confirmVerified(mockNetwork, mockRepo)
    }
}