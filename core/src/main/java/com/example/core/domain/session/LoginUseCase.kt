package com.example.core.domain.session

import com.example.core.IoDispatcher
import com.example.core.domain.session.model.Member
import com.example.core.utils.UseCase
import com.example.core.utils.Result
import com.example.core.utils.convert
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userSessionRepository: SessionRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : UseCase<String, Member>(coroutineDispatcher) {

    override suspend fun execute(parameter: String): Member {
        return userSessionRepository.login(parameter).convert {
            when (it) {
                is Result.Success -> {
                    //Save token, refresh token, member
                    userSessionRepository.saveToken(it.data.token)
                    userSessionRepository.saveRefreshToken(it.data.refreshToken)
                    it.data.member//emit member to show user hello message.
                }
                is Result.Error -> throw Exception(it.exception)
            }

        }
    }
}
