package com.example.core.data.session

import com.example.core.domain.session.SessionRepository
import com.example.core.domain.session.SessionState
import com.example.core.domain.session.model.LoginResult
import com.example.core.domain.session.model.RefreshToken
import com.example.core.domain.session.model.Token
import com.example.core.utils.Result
import com.example.core.utils.convert
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(
    private val userSession: FirebaseUserSession,
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val cacheAccountDataSource: CacheAccountDataSource
) : SessionRepository {

    override suspend fun login(token: String): Result<LoginResult> {
        return remoteAccountDataSource.login(token).convert {
            when (it) {
                is Result.Success -> Result.Success(it.data.toDomain())
                is Result.Error -> Result.Error(it.exception)
            }
        }
    }

    override suspend fun logout(): Flow<Unit> {
        return userSession.logout()
    }

    override fun loadSessionState(): SessionState {
        return userSession.loadSessionState()
    }

    override suspend fun refreshToken(): Result<Token> {
        // Load token from local
        return cacheAccountDataSource.loadRefreshToken().convert { token ->
            // If token is null, just make Token instance with empty string and pass it to refreshToken func. And it will fail on server.
            remoteAccountDataSource.refreshToken(Token(token ?: "")).convert { result ->
                when (result) {
                    is Result.Success -> Result.Success(result.data.createToken())
                    is Result.Error -> Result.Error(result.exception)
                }
            }
        }
    }

    override suspend fun saveToken(token: Token): Result<Unit> {
        return cacheAccountDataSource.saveToken(token)
    }

    override suspend fun saveRefreshToken(token: RefreshToken): Result<Unit> {
        return cacheAccountDataSource.saveRefreshToken(token)
    }

}
