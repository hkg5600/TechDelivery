package com.example.data.session

import com.example.domain.NoTokenFoundException
import com.example.domain.session.SessionRepository
import com.example.domain.session.SessionState
import com.example.domain.session.model.RefreshToken
import com.example.domain.session.model.Token
import com.example.domain.utils.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(
    private val userSession: FirebaseUserSession,
    private val remoteAccountDataSource: RemoteAccountDataSource,
    private val cacheAccountDataSource: CacheAccountDataSource
) : SessionRepository {

    override fun logout(): Flow<Unit> {
        return userSession.logout()
    }

    override fun loadSessionState(): Flow<SessionState> {
        return userSession.loadSessionState()
    }

    @FlowPreview
    override fun refreshToken(): Flow<Result<Token>> {
        // loadToken from local
        return cacheAccountDataSource.loadToken().flatMapConcat {
            // if token is null, just make Token instance and refresh token. it should fail.
            remoteAccountDataSource.refreshToken(Token(it ?: "")).map { result ->
                when (result) {
                    is Result.Loading -> Result.Loading
                    is Result.Success -> Result.Success(Token(result.data.token))
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
