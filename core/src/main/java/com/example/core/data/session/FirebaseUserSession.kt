package com.example.core.data.session

import com.example.core.domain.session.SessionState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FirebaseUserSession @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun logout() : Flow<Unit> = flow {
        emit(firebaseAuth.signOut())
    }

    fun loadSessionState() : SessionState {
        return if (firebaseAuth.currentUser == null) { // currentUser == null means that user never logged in or logged out
            SessionState.LOGOUT
        } else {
            SessionState.LOGIN
        }
    }

}