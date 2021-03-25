package com.example.domain.session

import com.example.domain.session.model.RefreshToken
import com.example.domain.session.model.Token
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun logout() : Flow<Unit>
    fun loadSessionState() : Flow<SessionState>
    fun refreshToken(): Flow<Result<Token>>
    fun saveToken(token: Token) : Flow<Result<Unit>>
    fun saveRefreshToken(token: RefreshToken) : Flow<Result<Unit>>

}
