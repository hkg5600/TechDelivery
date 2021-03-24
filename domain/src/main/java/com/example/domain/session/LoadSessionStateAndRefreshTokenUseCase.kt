package com.example.domain.session

import com.example.domain.utils.FlowUseCase
import com.example.domain.utils.IoDispatcher
import com.example.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadSessionStateAndRefreshTokenUseCase @Inject constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val sessionRepository: SessionRepository
) : FlowUseCase<Unit, SessionState>(coroutineDispatcher) {
    override fun executeFlow(parameter: Unit): Flow<Result<SessionState>> {
        return flow {
            emit(Result.Loading)
            sessionRepository.loadSessionState().map { sessionState ->
                if (sessionState == SessionState.LOGOUT) { // if SessionState is LOGOUT, emit LOGOUT
                    emit(Result.Success(sessionState))
                }
                sessionRepository.refreshToken().map { // if SessionState is LOGIN, refresh token. if refresh fails, RE_LOGIN, succeed, LOGIN
                    if (it is Result.Success) {
                        //Todo save token
                        emit(Result.Success(SessionState.LOGIN))
                    } else {
                        emit(Result.Success(SessionState.RE_LOGIN))
                    }
                }
            }
        }
    }
}