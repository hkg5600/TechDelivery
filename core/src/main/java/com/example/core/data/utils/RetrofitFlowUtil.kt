package com.example.core.data.utils

import retrofit2.Response
import com.example.core.utils.Result

fun <T> Response<BaseResponse<T>>.verify(): Result<T> {
    if (!this.isSuccessful) {
        return Result.Error(Exception("${this.code()}"))
    }

    return when (this.body()?.status) {
        in 200..299 -> {
            val body = this.body()?.data
            if (body == null) Result.Error(Exception("Find no body")) else Result.Success(body)
        }
        else -> {
            Result.Error(Exception("${this.body()?.status}"))
        }
    }

}

fun <T> Response<BaseMessageResponse>.verifyMessage(): Result<String> {
    if (!this.isSuccessful) {
        return Result.Error(Exception("${this.code()}"))
    }

    return when (this.body()?.status) {
        in 200..299 -> {
            val message = this.body()?.message
            if (message == null) Result.Error(Exception("Find no message")) else Result.Success(message)
        }
        else -> {
            Result.Error(Exception("${this.body()?.status}"))
        }
    }

}