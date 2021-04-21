package com.example.core.data.session

import com.example.core.data.session.api.UserService
import com.example.core.data.utils.verify
import com.example.core.data.session.model.GithubLoginPayload
import com.example.core.data.session.model.GithubLoginResponse
import com.example.core.data.session.model.RefreshTokenPayload
import com.example.core.data.session.model.RefreshTokenResponse
import com.example.core.domain.session.model.Token
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val userService: UserService
) {

    suspend fun login(code: String): Result<GithubLoginResponse> {
        val loginResult = userService.loginWithGithub(createGithubLoginPayload(code))
        return loginResult.verify()
    }


    suspend fun refreshToken(token: Token): Result<RefreshTokenResponse> {
        val refreshTokenResponse = userService.refreshToken(createRefreshTokenPayload(token))
        return refreshTokenResponse.verify()
    }

    private fun createGithubLoginPayload(code: String) = GithubLoginPayload(code)

    private fun createRefreshTokenPayload(token: Token) = RefreshTokenPayload(token.token)
}
