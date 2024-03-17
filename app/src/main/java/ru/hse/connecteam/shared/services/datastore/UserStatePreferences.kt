package ru.hse.connecteam.shared.services.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStatePreferences @Inject constructor(
    @DataStoreModule.StateDataStore private val dataStore: DataStore<Preferences>
) {
    companion object {
        private const val GAME_INVITE = "GameInvite"
        private const val TARIFF_INVITE = "TariffInvite"
        val gameInviteKey = stringPreferencesKey(GAME_INVITE)
        val tariffInviteKey = stringPreferencesKey(TARIFF_INVITE)
    }

    fun getGameInvite(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[gameInviteKey] ?: ""
        }
    }

    suspend fun setGameInvite(gameInvite: String) {
        dataStore.edit { preference ->
            preference[gameInviteKey] = gameInvite
        }
    }

    fun getTariffInvite(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences[tariffInviteKey] ?: ""
        }
    }

    suspend fun setTariffInvite(tariffInvite: String) {
        dataStore.edit { preference ->
            preference[tariffInviteKey] = tariffInvite
        }
    }
}