package com.example.core.domain.session

import com.example.core.domain.session.model.LoginResult
import com.example.core.domain.session.model.RefreshToken
import com.example.core.utils.Result
import com.example.core.domain.session.model.Token
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun login(token: String) : Flow<Result<LoginResult>>
    fun logout() : Flow<Unit>
    fun loadSessionState() : Flow<SessionState>
    fun refreshToken(): Flow<Result<Token>>
    fun saveToken(token: Token) : Flow<Result<Unit>>
    fun saveRefreshToken(token: RefreshToken) : Flow<Result<Unit>>

}
