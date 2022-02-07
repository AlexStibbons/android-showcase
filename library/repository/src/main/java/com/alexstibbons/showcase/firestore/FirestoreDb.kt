package com.alexstibbons.showcase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

interface FirestoreDb {
    suspend fun getFavesForUser(id: String)
}

private const val USER_FAVE_COL = "user-faves"
private const val MEDIA = "media"

internal data class FirestoreImpl(
    private val db: FirebaseFirestore
): FirestoreDb {

    private val userFaveCollection by lazy { db.collection(USER_FAVE_COL) }

    override suspend fun getFavesForUser(id: String) {
        userFaveCollection.document(id).collection(MEDIA).get() // get all docs from subcollection media
            
    }


}