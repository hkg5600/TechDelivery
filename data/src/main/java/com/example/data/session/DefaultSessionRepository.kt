package com.example.data.session

import com.example.domain.session.SessionRepository
import com.example.domain.session.SessionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSessionRepository @Inject constructor(
    private val userSession: FirebaseUserSession,
    private val remoteAccountDataSource: RemoteAccountDataSource
) : SessionRepository {

    override fun logout(): Flow<Unit> {
        return userSession.logout()
    }

    override fun getSessionState(): Flow<SessionState> {
       return userSession.getSessionState().map {
           if (it == SessionState.LOGOUT) {
               it
               return@map
           }


       }
    }


}
