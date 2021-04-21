package com.example.core.utils

sealed class Result<out R> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

}

suspend fun <I, O> I.convert(converter: suspend (input: I) -> O) : O {
    return converter(this)
}

suspend fun <R> Result<R>.execute(executor: suspend (result : Result<R>) -> Unit) {
    executor(this)
}

