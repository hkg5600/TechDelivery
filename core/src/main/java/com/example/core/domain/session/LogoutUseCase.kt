package com.example.core.domain.session

import com.example.core.utils.UseCase
import com.example.core.IoDispatcher
import com.example.core.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: SessionRepository
) : UseCase<Unit, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: Unit) {
        repository.logout()
    }
}