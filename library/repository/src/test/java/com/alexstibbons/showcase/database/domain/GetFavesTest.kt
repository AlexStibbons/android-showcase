package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.assertSuccess
import com.alexstibbons.showcase.database.FaveEntity
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.mockFaveEntity
import com.alexstibbons.showcase.responses.Response
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

internal class GetFavesTest {

    private val mockRepo: FaveRepository = mockk()

    private lateinit var useCase: GetFaves

    @Before
    fun setUp() {
        clearAllMocks()
        useCase = GetFaves(mockRepo)
    }

    @Test
    fun `when there are no faves, use case should return an empty list success`() {
        coEvery { mockRepo.getFaves() } returns Response.success(emptyList())

        val result = runBlocking { useCase.run(null) }

        result.assertSuccess(emptyList<FaveEntity>())
        coVerify { mockRepo.getFaves() }
        confirmVerified(mockRepo)
    }

    @Test
    fun `when there are faves, use case should return a list of fave entities`() {
        coEvery { mockRepo.getFaves() } returns Response.success(listOf(mockFaveEntity))

        val result = runBlocking { useCase.run(null) }

        result.assertSuccess(listOf(mockFaveEntity))
        coVerify { mockRepo.getFaves() }
        confirmVerified(mockRepo)
    }
}