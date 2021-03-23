package com.example.data.session

import com.example.domain.session.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(
    private val userSession: RemoteUserSessionDataSource
) : SessionRepository {

    override fun logout(): Flow<Unit> {
        return userSession.logout()
    }

    override fun userLoggedIn(): Flow<Boolean> {
       return userSession.userLoggedIn()
    }


}
