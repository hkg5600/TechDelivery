package com.example.techdelivery.login

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.core.domain.session.LoginUseCase
import com.example.core.utils.Event
import com.example.core.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class LoginViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    val reLogin = savedStateHandle.getLiveData<Boolean>("RE_LOGIN")

    val email = ObservableField<String>("")

    private val _loginButtonEnabled = MutableLiveData<Boolean>()
    val loginButtonEnabled : LiveData<Boolean>
        get() = _loginButtonEnabled

    private val _startLoginFlow = MutableLiveData<Event<String>>()
    val startLoginFlow : LiveData<Event<String>>
        get() = _startLoginFlow

    private val _loginError = MutableLiveData<Event<String>>()
    val loginError : LiveData<Event<String>>
        get() = _loginError

    private val _navigateToMain = MutableLiveData<Event<Unit>>()
    val navigateToMain : LiveData<Event<Unit>>
        get() = _navigateToMain

    private val _loading = MutableLiveData<Boolean>(false)
    val loading : LiveData<Boolean>
        get() = _loading

    private val emailChangeCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            _loginButtonEnabled.value = !email.get().isNullOrBlank()
        }
    }

    init {
        email.addOnPropertyChangedCallback(emailChangeCallback)
    }

    fun startLoginFlow() {
        _startLoginFlow.value = Event(email.get() ?: "")
    }

    fun login(token: String) {
        viewModelScope.launch {
            _loading.value = false
            loginUseCase(token).collect {
                when (it) {
                    is Result.Success -> navigateToMain()
                    is Result.Error -> _loginError.value = Event(it.exception.message ?: "Unknown")
                    is Result.Loading -> _loading.value = true
                }
            }
        }
    }

    fun navigateToMain() {
        _navigateToMain.value = Event(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        email.removeOnPropertyChangedCallback(emailChangeCallback)
    }
}