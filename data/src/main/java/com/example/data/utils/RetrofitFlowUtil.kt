package com.example.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

fun <T> Response<BaseResponse<T>>.verify(): T {
    if (!this.isSuccessful) {
        throw Exception("${this.code()}")
    }

    when (this.body()?.status) {
        in 200..299 -> {
            return this.body()?.data ?: throw Exception("Find no body")
        }
        else -> {
            throw Exception("${this.body()?.status}")
        }
    }

}

fun <T> Response<BaseMessageResponse>.verifyMessage(): String {
    if (!this.isSuccessful) {
        throw Exception("${this.code()}")
    }

    when (this.body()?.status) {
        in 200..299 -> {
            return this.body()?.message ?: throw Exception("Find no body")
        }
        else -> {
            throw Exception("${this.body()?.status}")
        }
    }

}