package com.example.data.session

import com.example.data.session.api.UserService
import com.example.data.session.model.GithubLoginPayload
import com.example.data.session.model.GithubLoginResponse
import com.example.data.session.model.MemberRemote
import com.example.data.utils.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteAccountDataSource @Inject constructor(
    private val userService: UserService
) {

    fun login(code: String) : Flow<GithubLoginResponse> {
        return flow {
            val loginResult = userService.loginWithGithub(createGithubLoginPayload(code))
            emit(loginResult.verify())
        }
    }


    private fun createGithubLoginPayload(code: String) = GithubLoginPayload(code)
}