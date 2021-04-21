package com.example.core.domain.session

import com.example.core.IoDispatcher
import com.example.core.utils.Result
import com.example.core.utils.UseCase
import com.example.core.utils.convert
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LoadSessionStateAndRefreshTokenUseCase @Inject constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val sessionRepository: SessionRepository
) : UseCase<Unit, SessionState>(coroutineDispatcher) {
    override suspend fun execute(parameter: Unit): SessionState {
        return if (sessionRepository.loadSessionState() == SessionState.LOGOUT) {
            SessionState.LOGOUT
        } else {
            sessionRepository.refreshToken().convert {
                if (it is Result.Success) {
                    //  sessionRepository.saveToken(it.data)
                    SessionState.LOGIN
                } else {
                    // RE_LOGIN means user logged in before but token expired
                    SessionState.RE_LOGIN
                }
            }
        }

    }
}