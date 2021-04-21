package com.example.core.data.utils

data class BaseResponse<T>(
    val message: String,
    val status : Int,
    val data: T
)