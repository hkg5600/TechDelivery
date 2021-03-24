package com.example.data.session.model

data class MemberRemote(
    val accessLevel: Int,
    val profileImage: String?,
    val introduce: String?,
    val memberName: String,
    val memberId: String,
)