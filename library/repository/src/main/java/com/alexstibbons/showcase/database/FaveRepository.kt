package com.alexstibbons.showcase.database

import com.alexstibbons.showcase.movieApi.MediaFailure
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

interface FaveRepository {
    suspend fun getFaves(): Response<Failure, List<FaveEntity>>
    suspend fun getFavesIds(): Response<Failure, List<Int>>
    suspend fun addToFave(fave: FaveEntity): Response<Failure, Unit>
    suspend fun removeFave(id: Int): Response<Failure, Unit>
}

internal class FaveRepositoryImpl(
    private val cache: FaveCache
): FaveRepository {

    override suspend fun getFaves(): Response<Failure, List<FaveEntity>> {
        val dbResponse = cache.getFaves()

        return if (dbResponse.isNullOrEmpty()) Response.failure(MediaFailure.EmptyMediaList) else Response.success(dbResponse)
    }


    override suspend fun getFavesIds(): Response<Failure, List<Int>> {
        val dbResponse = cache.getFavesIds()

        return if (dbResponse.isNullOrEmpty()) Response.failure(MediaFailure.EmptyMediaList) else Response.success(dbResponse)

    }

    override suspend fun addToFave(fave: FaveEntity): Response<Failure, Unit> {
        val dbResponse = cache.addToFave(fave)

        return if (dbResponse > -1) Response.success(Unit) else Response.failure(Failure.ServerError)
    }

    override suspend fun removeFave(id: Int): Response<Failure, Unit> {
        val dbResponse = cache.removeFave(id)

        return Response.success(Unit)
    }

}