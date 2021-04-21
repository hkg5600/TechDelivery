package com.example.techdelivery.login

import android.util.Patterns
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.core.domain.session.LoginUseCase
import com.example.core.utils.Event
import com.example.core.utils.Result
import com.example.core.utils.execute
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    val reLogin = savedStateHandle.getLiveData<Boolean>("RE_LOGIN")

    val email = ObservableField<String>("")

    private val _startLoginFlow = MutableLiveData<Event<String>>()
    val startLoginFlow : LiveData<Event<String>>
        get() = _startLoginFlow

    private val _loginError = MutableLiveData<Event<String>>()
    val loginError : LiveData<Event<String>>
        get() = _loginError

    private val _navigateToMain = MutableLiveData<Event<Unit>>()
    val navigateToMain : LiveData<Event<Unit>>
        get() = _navigateToMain

    private val _emailIsNotValid = MutableLiveData<Event<Unit>>()
    val emailIsNotValid : LiveData<Event<Unit>>
        get() = _emailIsNotValid

    private val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean>
        get() = _loading

    fun startLoginFlow() {
        if (checkEmailIsValid()) {
            _startLoginFlow.value = Event(email.get() ?: "")
        } else {
            _emailIsNotValid.value = Event(Unit)
        }

    }

    private fun checkEmailIsValid(): Boolean {
       return Patterns.EMAIL_ADDRESS.matcher(email.get() ?: "").matches()
    }

    fun login(token: String) {
        viewModelScope.launch {
            _loading.value = true
            loginUseCase(token).execute {
                when (it) {
                    is Result.Success -> navigateToMain()
                    is Result.Error -> _loginError.value = Event(it.exception.message ?: "Unknown")
                }
            }
            _loading.value = false
        }
    }

    fun navigateToMain() {
        _navigateToMain.value = Event(Unit)
    }

}