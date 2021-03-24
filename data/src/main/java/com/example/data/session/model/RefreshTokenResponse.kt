package com.example.data.session.model

import com.example.domain.session.model.RefreshToken

data class RefreshTokenResponse(
    val token : String,
) {
    fun toDomain() : RefreshToken {
        return RefreshToken(
            token = token
        )
    }
}