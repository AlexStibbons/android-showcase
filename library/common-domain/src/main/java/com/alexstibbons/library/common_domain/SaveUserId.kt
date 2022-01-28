package com.alexstibbons.library.common_domain

import com.alexstibbons.showcase.datastore.DataStorePref
import com.alexstibbons.showcase.interactors.CommandUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

class SaveUserId(
    private val auth: DataStorePref
): CommandUseCase<String>() {
    override suspend fun run(params: String?): Response<Failure, Unit> {
        require(params != null) {"Params cannot be null"}

        auth.saveId(params)

        return Response.success(Unit)
    }
}