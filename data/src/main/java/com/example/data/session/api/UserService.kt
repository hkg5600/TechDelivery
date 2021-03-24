package com.example.data.session.api

import com.example.data.session.model.GithubLoginPayload
import com.example.data.session.model.GithubLoginResponse
import com.example.data.session.model.RefreshTokenPayload
import com.example.data.session.model.RefreshTokenResponse
import com.example.data.utils.BaseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @POST("/login/with-github")
    suspend fun loginWithGithub(
        @Body code: GithubLoginPayload
    ) : Response<BaseResponse<GithubLoginResponse>>

    @POST("/api/token")
    suspend fun refreshToken(
        @Body refreshToken: RefreshTokenPayload
    ) : Response<BaseResponse<RefreshTokenResponse>>

}