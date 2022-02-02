package com.alexstibbons.showcase.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

interface ProtoDataStore {
    suspend fun saveUser()
    suspend fun getUser()
}

/*object DataStoreSerializer : Serializer<UserMsg> {
    override val defaultValue: UserMsg
        get() = UserMsg.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserMsg {
        try {
            return UserMsg.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: UserMsg, output: OutputStream) {
        t.writeTo(output)
    }

}

private val Context.protoDataStore: DataStore<UserMsg> by dataStore(fileName = "auth_proto_data_store", serializer = DataStoreSerializer)*/

internal class ProtoDataStoreImpl(
    context: Context
): ProtoDataStore {




    override suspend fun saveUser() {
        TODO("Not yet implemented")
    }

    override suspend fun getUser() {
        TODO("Not yet implemented")
    }
}