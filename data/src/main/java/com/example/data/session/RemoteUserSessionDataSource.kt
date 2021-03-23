package com.example.data.session

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RemoteUserSessionDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun logout() : Flow<Unit> = flow {
        emit(firebaseAuth.signOut())
    }

    fun userLoggedIn() : Flow<Boolean> = flow {
        emit(firebaseAuth.currentUser != null)
    }

}