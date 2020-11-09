package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.assertSuccess
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.responses.Response
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

internal class GetFaveIdsTest {

    private val mockRepo: FaveRepository = mockk()

    private lateinit var useCase: GetFaveIds

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = GetFaveIds(mockRepo)
    }

    @Test
    fun `when there are no faves, use case should return an empty list success`() {
        coEvery { mockRepo.getFavesIds() } returns Response.success(emptyList())

        val result = runBlocking { useCase.run(null) }

        result.assertSuccess(emptyList<Int>())
        coVerify { mockRepo.getFavesIds() }
        confirmVerified(mockRepo)
    }

    @Test
    fun `when there are faves, use case should return a list oof their ids`() {
        coEvery { mockRepo.getFavesIds() } returns Response.success(listOf(1, 2, 3))

        val result = runBlocking { useCase.run(null) }

        result.assertSuccess(listOf(1, 2, 3))
        coVerify { mockRepo.getFavesIds() }
        confirmVerified(mockRepo)
    }
}