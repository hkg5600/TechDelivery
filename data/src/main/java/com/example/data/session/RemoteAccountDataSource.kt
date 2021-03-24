package com.example.data.session

import com.example.data.session.api.UserService
import com.example.data.session.model.*
import com.example.data.utils.verify
import com.example.domain.session.model.Token
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val userService: UserService
) {

    fun login(code: String) : Flow<Result<GithubLoginResponse>> {
        return flow {
            val loginResult = userService.loginWithGithub(createGithubLoginPayload(code))
            emit(loginResult.verify())
        }
    }


    fun refreshToken(token: Token) : Flow<Result<RefreshTokenResponse>> {
        return flow {
            emit(Result.Loading)
            val refreshTokenResponse = userService.refreshToken(createRefreshTokenPayload(token))
            emit(refreshTokenResponse.verify())
        }
    }

    private fun createGithubLoginPayload(code: String) = GithubLoginPayload(code)

    private fun createRefreshTokenPayload(token: Token) = RefreshTokenPayload(token.token)
}
