package com.alexstibbons.library.common_domain

import com.alexstibbons.showcase.database.FaveRepository
import com.alexstibbons.showcase.datastore.DataStorePref
import com.alexstibbons.showcase.interactors.CommandUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

class Logout(
    private val auth: DataStorePref,
    private val faveRepo: FaveRepository
): CommandUseCase<Unit>() {
    override suspend fun run(params: Unit?): Response<Failure, Unit> {

        auth.clear()
        faveRepo.clear()

        return Response.success(Unit)
    }
}