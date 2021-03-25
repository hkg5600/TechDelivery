package com.example.data.session

import com.example.data.preference.PreferenceDataStore
import com.example.domain.NoTokenFoundException
import com.example.domain.session.model.RefreshToken
import com.example.domain.session.model.Token
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheAccountDataSource @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) {

    fun loadToken(): Flow<String?> {
        return preferenceDataStore.loadToken()
    }

    fun saveToken(token : Token) : Flow<Result<Unit>> {
        return preferenceDataStore.saveToken(token)
    }

    fun saveRefreshToken(token : RefreshToken) : Flow<Result<Unit>> {
        return preferenceDataStore.saveRefreshToken(token)
    }

}
