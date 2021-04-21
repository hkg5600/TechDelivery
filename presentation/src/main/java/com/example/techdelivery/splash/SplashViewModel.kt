package com.example.techdelivery.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.session.LoadSessionStateAndRefreshTokenUseCase
import com.example.core.domain.session.SessionState
import com.example.core.utils.Event
import com.example.core.utils.Result
import com.example.core.utils.execute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loadSessionStateAndRefreshTokenUseCase: LoadSessionStateAndRefreshTokenUseCase,
) : ViewModel() {

    private val _error = MutableLiveData<Event<Unit>>()
    val error: LiveData<Event<Unit>>
        get() = _error

    private val _navigateToMain = MutableLiveData<Event<Unit>>()
    val navigateToMain : LiveData<Event<Unit>>
        get() = _navigateToMain

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin : LiveData<Event<Boolean>>
        get() = _navigateToLogin

    init {
        viewModelScope.launch {
            loadSessionStateAndRefreshTokenUseCase(Unit).let {
                when (it) {
                    is Result.Success -> verifySessionState(it.data)
                    is Result.Error -> _error.value = Event(Unit)
                }
            }

        }
    }

    private fun verifySessionState(sessionState: SessionState) {
        when (sessionState) {
            SessionState.LOGOUT -> {
                //Go to LoginActivity
                _navigateToLogin.value = Event(false)
            }
            SessionState.LOGIN -> {
                //Pass to MainActivity
                _navigateToMain.value = Event(Unit)
            }
            SessionState.RE_LOGIN -> {
                //Go to LoginActivity with RE_LOGIN command
                _navigateToLogin.value = Event(true)
            }
        }
    }


}