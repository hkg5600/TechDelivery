package com.example.core.data.session.model

import com.example.core.domain.session.model.LoginResult
import com.example.core.domain.session.model.Member
import com.example.core.domain.session.model.RefreshToken
import com.example.core.domain.session.model.Token

data class GithubLoginResponse(
    val token: String,
    val refreshToken: String,
    val member: MemberRemote,
) {
    fun toDomain() : LoginResult {
        return LoginResult(
            token = Token(token),
            refreshToken = RefreshToken(refreshToken),
            member = Member(
                accessLevel = member.accessLevel,
                profileImage = member.profileImage,
                introduce = member.introduce,
                memberId = member.memberId,
                memberName = member.memberName
            )
        )
    }
}