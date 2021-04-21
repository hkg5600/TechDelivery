package com.example.core.data.session.model

import com.example.core.domain.session.model.Token

data class RefreshTokenResponse(
    val token : String,
) {
    fun createToken() : Token {
        return Token(
            token = token
        )
    }
}