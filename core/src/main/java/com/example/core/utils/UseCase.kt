package com.example.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlin.Exception

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    protected abstract suspend fun execute(parameter: P) : R

    suspend operator fun invoke(parameter : P) : Result<R> {
        return try {
            Result.Success(execute(parameter))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}