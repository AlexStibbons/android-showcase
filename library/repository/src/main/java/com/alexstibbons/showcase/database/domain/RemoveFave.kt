package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

data class RemoveFave(
    private val faveRepo: FaveRepository
): QueryUseCase<Unit, Int>() {

    override suspend fun run(params: Int?): Response<Failure, Unit> {
      require(params != null) {"Id of media you want to remove cannot be null"}

        return faveRepo.removeFave(params)
    }
}