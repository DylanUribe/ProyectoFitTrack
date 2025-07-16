package com.example.proyectofittrack

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "user_prefs")

    private val NAME_KEY = stringPreferencesKey("user_name")
    private val AGE_KEY = stringPreferencesKey("user_age")  // Guardaremos como String para simplificar
    private val PHOTO_URI_KEY = stringPreferencesKey("user_photo_uri")

    val userNameFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[NAME_KEY] ?: "" }

    val userAgeFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[AGE_KEY] ?: "" }

    val userPhotoUriFlow: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[PHOTO_URI_KEY] ?: "" }

    suspend fun saveUserData(name: String, age: String, photoUri: String?) {
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[AGE_KEY] = age
            if (photoUri != null) {
                preferences[PHOTO_URI_KEY] = photoUri
            }
        }
    }
}
