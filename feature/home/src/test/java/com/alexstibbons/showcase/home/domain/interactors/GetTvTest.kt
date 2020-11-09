package com.alexstibbons.showcase.home.domain.interactors

import com.alexstibbons.showcase.home.*
import com.alexstibbons.showcase.home.assertFailure
import com.alexstibbons.showcase.home.assertSuccess
import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import com.alexstibbons.showcase.tvApi.TvRepository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

internal class GetTvTest {

    private val mockNetwork: NetworkHandler = mockk()
    private val mockRepo: TvRepository = mockk()

    private lateinit var useCase: GetTv

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = GetTv(mockNetwork, mockRepo)
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

        val result = runBlocking { useCase.run(1) }

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
        coEvery { mockRepo.getPopular(1) } returns Response.failure(MediaFailure.EmptyMediaList)

        val result = runBlocking { useCase.run(1) }

        result.assertFailure(MediaFailure.EmptyMediaList)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo.getPopular(1)
        }
        confirmVerified(mockNetwork, mockRepo)
    }

    @Test
    fun `when repo returns a film, use case should return success`() {
        every { mockNetwork.isConnected } returns true
        coEvery { mockRepo.getPopular(1) } returns Response.success(mockTvListResponse)

        val result = runBlocking { useCase.run(1) }

        result.assertSuccess(mockTvListDomain)
        coVerifyAll {
            mockNetwork.isConnected
            mockRepo.getPopular(1)
        }
        confirmVerified(mockNetwork, mockRepo)
    }
}