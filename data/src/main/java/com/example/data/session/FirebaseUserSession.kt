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
        if (firebaseAuth.currentUser == null) { // currentUser == null means that user never logged in or logged out
            emit(SessionState.LOGOUT)
        } else {
            emit(SessionState.LOGIN)
        }
    }

}