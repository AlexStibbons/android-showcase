package com.alexstibbons.library.common_domain

import com.alexstibbons.showcase.datastore.DataStoreFailure
import com.alexstibbons.showcase.datastore.DataStorePref
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

class GetUserId(
    private val auth: DataStorePref
): QueryUseCase<String, Unit>() {
    override suspend fun run(params: Unit?): Response<Failure, String> {

        val id = auth.getId()

        return if (id == null) Response.failure(DataStoreFailure.NoUserId) else Response.success(id)
    }

}