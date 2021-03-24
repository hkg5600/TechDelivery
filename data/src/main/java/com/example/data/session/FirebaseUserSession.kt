package com.example.data.session

import com.example.domain.session.SessionState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FirebaseUserSession @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun logout() : Flow<Unit> = flow {
        emit(firebaseAuth.signOut())
    }

    fun loadSessionState() : Flow<SessionState> = flow {
        if (firebaseAuth.currentUser == null) { // if current user == null 이라면 유저가 한 번도 로그인을 하지 않았거나 로그아웃한 상태이다.
            emit(SessionState.LOGOUT)
        } else {
            emit(SessionState.LOGIN)
        }
    }

}