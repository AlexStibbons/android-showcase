package com.alexstibbons.showcase.details

import com.alexstibbons.showcase.details.domain.toFilmDetails
import com.alexstibbons.showcase.details.domain.toTvDetails
import org.junit.Test
import org.junit.Assert.*

internal class MappersTest {

    @Test
    fun `film entity should map to film details`() {

        val result = mockFilmEntity.toFilmDetails()

        assertEquals(mockFilmDetails, result)
    }

    @Test
    fun `tv entity should map to tv details`() {

        val result = mockTvEntity.toTvDetails()

        assertEquals(mockTvDetails, result)
    }
}