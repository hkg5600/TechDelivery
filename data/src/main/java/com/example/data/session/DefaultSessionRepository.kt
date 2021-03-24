package com.example.data.session

import com.example.domain.session.SessionRepository
import com.example.domain.session.SessionState
import com.example.domain.session.model.RefreshToken
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
    override fun refreshToken(): Flow<Result<RefreshToken>> {
        return cacheAccountDataSource.loadToken().flatMapConcat { // loadToken
            remoteAccountDataSource.refreshToken(it).map { result ->
                when (result) {
                    is Result.Loading -> Result.Loading
                    is Result.Success -> Result.Success(result.data.toDomain())
                    is Result.Error -> Result.Error(result.exception)
                }

            }

        }
    }

}
