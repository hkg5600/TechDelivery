package com.example.core.domain.session.model

data class LoginResult(
    val token : Token,
    val refreshToken: RefreshToken,
    val member: Member
)