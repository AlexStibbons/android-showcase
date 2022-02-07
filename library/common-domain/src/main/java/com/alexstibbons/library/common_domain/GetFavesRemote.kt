package com.alexstibbons.library.common_domain

import com.alexstibbons.showcase.firestore.FirestoreDb
import com.alexstibbons.showcase.interactors.QueryUseCase
import com.alexstibbons.showcase.network.NetworkHandler
import com.alexstibbons.showcase.responses.Failure
import com.alexstibbons.showcase.responses.Response

class GetFavesRemote(
    private val networkHandler: NetworkHandler,
    private val firestoreDb: FirestoreDb
): QueryUseCase<String, GetFavesRemote.Params>() {

    class Params(val userId: String)

    override suspend fun run(params: Params?): Response<Failure, String> {
        require( params != null) { "Page number cannot be null" }

        if (!networkHandler.isConnected) return Response.failure(Failure.NetworkConnection)

        return Response.success("yay")
    }
}