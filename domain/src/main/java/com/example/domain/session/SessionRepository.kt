package com.example.domain.session

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    fun logout() : Flow<Unit>
    fun getSessionState() : Flow<SessionState>

}
