package com.example.core.data.session

import com.example.core.domain.session.SessionRepository
import com.example.core.domain.session.SessionState
import com.example.core.domain.session.model.LoginResult
import com.example.core.domain.session.model.RefreshToken
import com.example.core.domain.session.model.Token
import com.example.core.utils.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(
    private val userSession: FirebaseUserSession,
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val cacheAccountDataSource: CacheAccountDataSource
) : SessionRepository {

    override fun login(token: String): Flow<Result<LoginResult>> {
        return remoteAccountDataSource.login(token).map {
            when (it) {
                is Result.Success -> Result.Success(it.data.toDomain())
                is Result.Error -> Result.Error(it.exception)
                is Result.Loading -> Result.Loading
            }
        }
    }

    override fun logout(): Flow<Unit> {
        return userSession.logout()
    }

    override fun loadSessionState(): Flow<SessionState> {
        return userSession.loadSessionState()
    }

    @FlowPreview
    override fun refreshToken(): Flow<Result<Token>> {
        // Load token from local
        return cacheAccountDataSource.loadToken().flatMapConcat { token ->
            // If token is null, just make Token instance with empty string and pass it to refreshToken func. And it will fail on server.
            remoteAccountDataSource.refreshToken(Token(token ?: "")).map { result ->
                when (result) {
                    is Result.Loading -> Result.Loading
                    is Result.Success -> Result.Success(result.data.createToken())
                    is Result.Error -> Result.Error(result.exception)
                }

            }

        }
    }

    override fun saveToken(token: Token): Flow<Result<Unit>> {
        return cacheAccountDataSource.saveToken(token)
    }

    override fun saveRefreshToken(token: RefreshToken): Flow<Result<Unit>> {
        return cacheAccountDataSource.saveRefreshToken(token)
    }

}
