package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.assertFailure
import com.alexstibbons.showcase.assertSuccess
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.mockFaveEntity
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.lang.IllegalArgumentException

internal class SaveFaveTest {

    private val mockRepo: FaveRepository = mockk()

    private lateinit var useCase: SaveFave

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = SaveFave(mockRepo)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `when there's no params, use case should throw an exception`() {
        runBlocking { useCase.run(null) }

        coVerifyAll {
            mockRepo wasNot Called
        }
        confirmVerified(mockRepo)
    }

    @Test
    fun `when fave is saved, use case should return empty success`() {
        coEvery { mockRepo.addToFave(mockFaveEntity) } returns Response.success(Unit)

        val result = runBlocking { useCase.run(mockFaveEntity) }

        result.assertSuccess(Unit)
        coVerifyAll {
            mockRepo.addToFave(mockFaveEntity)
        }
        confirmVerified(mockRepo)
    }

    @Test
    fun `when fave is not saved, use case should return server error`() {
        coEvery { mockRepo.addToFave(mockFaveEntity) } returns Response.failure(Failure.ServerError)

        val result = runBlocking { useCase.run(mockFaveEntity) }

        result.assertFailure(Failure.ServerError)
        coVerifyAll {
            mockRepo.addToFave(mockFaveEntity)
        }
        confirmVerified(mockRepo)
    }
}