package com.example.domain.session

import com.example.domain.utils.FlowUseCase
import com.example.domain.utils.IoDispatcher
import com.example.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repository: SessionRepository
) : FlowUseCase<Unit, Unit>(coroutineDispatcher) {
    override fun executeFlow(parameter: Unit): Flow<Result<Unit>> {
        return flow {
            repository.logout()
            emit(Result.Success(Unit))
        }
    }
}