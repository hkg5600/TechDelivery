package com.example.core.domain.session

import com.example.core.domain.session.model.LoginResult
import com.example.core.domain.session.model.RefreshToken
import com.example.core.utils.Result
import com.example.core.domain.session.model.Token
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun login(token: String) : Result<LoginResult>
    suspend fun logout() : Flow<Unit>
    fun loadSessionState() : SessionState
    suspend fun refreshToken(): Result<Token>
    suspend fun saveToken(token: Token) : Result<Unit>
    suspend fun saveRefreshToken(token: RefreshToken) : Result<Unit>

}
