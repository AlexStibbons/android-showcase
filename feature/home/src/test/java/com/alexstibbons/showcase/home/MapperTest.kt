package com.alexstibbons.showcase.home

import com.alexstibbons.showcase.home.domain.toMovieListDomain
import com.alexstibbons.showcase.home.domain.toTvListDomain
import org.junit.Assert.*
import org.junit.Test

internal class MapperTest {
    @Test
    fun `movie list response should map to movie list domain`() {

        val result = mockFilmListResponse.toMovieListDomain()

        assertEquals(mockMovieListDomain, result)
    }

    @Test
    fun `tv list response should map to tv list domain`() {

        val result = mockTvListResponse.toTvListDomain()

        assertEquals(mockTvListDomain, result)
    }
}