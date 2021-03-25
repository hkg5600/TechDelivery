package com.example.data.session.model

import com.example.domain.session.model.RefreshToken
import com.example.domain.session.model.Token

data class RefreshTokenResponse(
    val token : String,
) {
    fun createToken() : Token {
        return Token(
            token = token
        )
    }
}