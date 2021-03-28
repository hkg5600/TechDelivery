package com.example.techdelivery.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LoginViewModel @ViewModelInject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val reLogin = savedStateHandle.getLiveData<Boolean>("RE_LOGIN")

}