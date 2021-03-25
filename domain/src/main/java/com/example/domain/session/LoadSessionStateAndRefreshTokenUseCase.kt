package com.example.domain.session

import com.example.domain.session.model.Token
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
            // Check firebase auth user state
            sessionRepository.loadSessionState().map { sessionState ->
                // If SessionState is LOGOUT, emit LOGOUT so that user can enter LoginActivity
                if (sessionState == SessionState.LOGOUT) {
                    emit(Result.Success(sessionState))
                }
                // Refresh token.
                sessionRepository.refreshToken().map {
                    // If refresh fails, emit RE_LOGIN, succeeds, save token and emit LOGIN
                    if (it is Result.Success) {
                        sessionRepository.saveToken(it.data)
                        emit(Result.Success(SessionState.LOGIN))
                    } else {
                        // RE_LOGIN means user logged in before but token expired
                        emit(Result.Success(SessionState.RE_LOGIN))
                    }
                }
            }
        }
    }
}