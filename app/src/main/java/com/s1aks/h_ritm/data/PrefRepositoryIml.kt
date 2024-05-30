package com.s1aks.h_ritm.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.s1aks.h_ritm.App
import com.s1aks.h_ritm.data.entities.PrefData
import com.s1aks.h_ritm.domain.PrefRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val USER_PREFERENCES_NAME = "h_ritm_pref"
private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class PrefRepositoryIml(
    private val dataStore: DataStore<Preferences> = App.appContext.dataStore
) : PrefRepository {

    private object UserKeys {
        val FIELD_AGE = intPreferencesKey("age")
    }

    private inline val Preferences.age
        get() = this[UserKeys.FIELD_AGE] ?: 0

    override val userPreferences: Flow<PrefData> = dataStore.data
        .catch {
            // throws an IOException when an error is encountered when reading data
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            PrefData(
                age = preferences.age
            )
        }
        .distinctUntilChanged()

    override suspend fun savePrefData(data: PrefData) {
        dataStore.edit {
            it[UserKeys.FIELD_AGE] = data.age
        }
    }
}