package com.example.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import com.example.domain.utils.Result

fun <T> Response<BaseResponse<T>>.verify(): Result<T> {
    if (!this.isSuccessful) {
        throw Exception("${this.code()}")
    }

    when (this.body()?.status) {
        in 200..299 -> {
            val body = this.body()?.data
            return if (body == null) Result.Error(Exception("Find no body")) else Result.Success(body)
        }
        else -> {
            throw Exception("${this.body()?.status}")
        }
    }

}

fun <T> Response<BaseMessageResponse>.verifyMessage(): Result<String> {
    if (!this.isSuccessful) {
        throw Exception("${this.code()}")
    }

    when (this.body()?.status) {
        in 200..299 -> {
            val message = this.body()?.message
            return if (message == null) Result.Error(Exception("Find no message")) else Result.Success(message)
        }
        else -> {
            throw Exception("${this.body()?.status}")
        }
    }

}