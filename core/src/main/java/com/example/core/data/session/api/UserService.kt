package com.example.core.data.session.api

import com.example.core.data.session.model.GithubLoginPayload
import com.example.core.data.session.model.GithubLoginResponse
import com.example.core.data.session.model.RefreshTokenPayload
import com.example.core.data.session.model.RefreshTokenResponse
import com.example.core.data.utils.BaseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("/login/with-github/mobile")
    suspend fun loginWithGithub(
        @Body token: GithubLoginPayload
    ) : Response<BaseResponse<GithubLoginResponse>>

    @POST("/api/token")
    suspend fun refreshToken(
        @Body refreshToken: RefreshTokenPayload
    ) : Response<BaseResponse<RefreshTokenResponse>>

}