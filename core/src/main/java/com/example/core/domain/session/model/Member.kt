package com.example.core.domain.session.model

data class Member(
    val accessLevel: Int,
    val profileImage: String?,
    val introduce: String?,
    val memberName: String,
    val memberId: String,
)