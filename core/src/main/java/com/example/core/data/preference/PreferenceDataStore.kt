package com.example.core.data.preference

import android.app.Application
import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.core.domain.session.model.RefreshToken
import com.example.core.domain.session.model.Token
import com.example.core.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val tokenStore: DataStore<Preferences> by lazy { context.createDataStore(name = TOKEN_STORE) }

    suspend fun saveToken(token: Token): Result<Unit> {

        val tokenPreferences = preferencesKey<String>(TOKEN_VALUE)
        tokenStore.edit {
            it[tokenPreferences] = token.token
        }
        return Result.Success(Unit)

    }

    suspend fun saveRefreshToken(token: RefreshToken): Result<Unit> {

        val tokenPreferences = preferencesKey<String>(REFRESH_TOKEN_VALUE)
        tokenStore.edit {
            it[tokenPreferences] = token.token
        }
        return Result.Success(Unit)

    }

    fun loadToken(): Flow<String?> {
        return flow {
            val tokenPreferences = preferencesKey<String>(TOKEN_VALUE)
            tokenStore.data.map {
                emit(it[tokenPreferences])
            }
        }
    }

    fun loadRefreshToken(): Flow<String?> {
        return flow {
            val tokenPreferences = preferencesKey<String>(REFRESH_TOKEN_VALUE)
            tokenStore.data.map {
                emit(it[tokenPreferences])
            }
        }
    }

    companion object {
        const val TOKEN_STORE = "TOKEN_STORE"
        const val TOKEN_VALUE = "TOKEN_VALUE"
        const val REFRESH_TOKEN_VALUE = "REFRESH_TOKEN_VALUE"
    }

}