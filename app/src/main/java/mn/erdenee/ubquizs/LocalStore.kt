package mn.erdenee.ubquizs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalStore(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("profile")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USER_ID_KEY = intPreferencesKey("id")
    }

    suspend fun saveUserData(token: String, id: Int) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_ID_KEY] = id
        }
    }

    val token: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }

    val userId: Flow<Int?> = context.dataStore.data.map { it[USER_ID_KEY] }
}