package com.alexstibbons.library.common_domain

import com.alexstibbons.showcase.datastore.DataStoreFailure
import com.alexstibbons.showcase.datastore.DataStorePref
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response
import kotlinx.coroutines.flow.singleOrNull

class GetUserId(
    private val auth: DataStorePref
): QueryUseCase<String, Unit>() {
    override suspend fun run(params: Unit?): Response<Failure, String> {

        val id: String? = auth.userId.singleOrNull()

        return if (id != null) Response.success(id) else Response.failure(DataStoreFailure.NoUserId)
    }

}