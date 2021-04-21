package com.example.core.data.session

import com.example.core.data.preference.PreferenceDataStore
import com.example.core.domain.session.model.RefreshToken
import com.example.core.domain.session.model.Token
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toCollection
import javax.inject.Inject

class CacheAccountDataSource @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) {

    suspend fun loadRefreshToken(): String? {
        return try {
            val token = preferenceDataStore.loadToken().single()
            token
        } catch (e: Exception) {
            null
        }
    }

    suspend fun loadToken(): String? {
        return try {
            val token = preferenceDataStore.loadToken().single()
            token
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveToken(token: Token): Result<Unit> {
        return preferenceDataStore.saveToken(token)
    }

    suspend fun saveRefreshToken(token: RefreshToken): Result<Unit> {
        return preferenceDataStore.saveRefreshToken(token)
    }

}
