package com.example.data.session.model

data class GithubLoginResponse(
    val token: String,
    val refreshToken: String,
    val member: MemberRemote,
)