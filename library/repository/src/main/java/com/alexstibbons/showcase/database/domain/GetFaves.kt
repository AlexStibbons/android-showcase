package com.alexstibbons.showcase.database.domain

import com.alexstibbons.showcase.database.FaveEntity
import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

data class GetFaves(
    private val faveRepo: FaveRepository
): QueryUseCase<List<FaveEntity>, Unit>() {
    override suspend fun run(params: Unit?): Response<Failure, List<FaveEntity>> = faveRepo.getFaves()
}