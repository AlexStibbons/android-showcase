package com.alexstibbons.showcase.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alexstibbons.showcase.responses.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

sealed class DataStoreFailure : Failure.FeatureSpecificFailure() {
    object NoUserId : DataStoreFailure()
}

interface DataStorePref {
    suspend fun saveId(id: String)
    suspend fun getId(): String?
    suspend fun clear()
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_data_store_pref")

internal class DataStorePrefImpl(
    private val context: Context
): DataStorePref {

    private val ds by lazy { context.dataStore }

    override suspend fun saveId(id: String) {
        ds.edit { settings ->
            settings[USER_ID] = id
        }
    }

    override suspend fun getId(): String? = ds.data.map { it[USER_ID] }.first()

    override suspend fun clear() {

    }

    companion object {
        private val USER_ID = stringPreferencesKey("userId")
    }
}