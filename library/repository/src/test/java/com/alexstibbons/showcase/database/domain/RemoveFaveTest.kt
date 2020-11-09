package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.assertSuccess
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.responses.Response
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

internal class RemoveFaveTest {

    private val mockRepo: FaveRepository = mockk()

    private lateinit var useCase: RemoveFave

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = RemoveFave(mockRepo)
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
        coEvery { mockRepo.removeFave(1) } returns Response.success(Unit)

        val result = runBlocking { useCase.run(1) }

        result.assertSuccess(Unit)
        coVerifyAll {
            mockRepo.removeFave(1)
        }
        confirmVerified(mockRepo)
    }
}