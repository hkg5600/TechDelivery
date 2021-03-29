package com.example.core.domain.session

import com.example.core.IoDispatcher
import com.example.core.domain.session.model.Member
import com.example.core.utils.FlowUseCase
import com.example.core.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userSessionRepository: SessionRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<String, Member>(coroutineDispatcher) {

    override fun executeFlow(parameter: String): Flow<Result<Member>> {
        return flow {
            userSessionRepository.login(parameter).map {
               when (it) {
                   is Result.Success -> {
                       //Save token, refresh token, member
                       userSessionRepository.saveToken(it.data.token)
                       userSessionRepository.saveRefreshToken(it.data.refreshToken)
                       emit(Result.Success(it.data.member)) //emit member to show user hello message.
                   }
                   is Result.Error -> emit(Result.Error(it.exception))
               }
            }
        }
    }
}
