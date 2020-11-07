package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.database.FaveEntity
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

data class SaveFave(
    private val faveRepo: FaveRepository
): QueryUseCase<Unit, FaveEntity>() {
    override suspend fun run(params: FaveEntity?): Response<Failure, Unit> {
        require(params != null) {"Params cannot be null"}

        return faveRepo.addToFave(params)
    }
}