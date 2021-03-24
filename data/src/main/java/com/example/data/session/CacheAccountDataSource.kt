package com.example.data.session

import com.example.domain.session.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheAccountDataSource @Inject constructor() {

    fun loadToken() : Flow<Token> {
        return flow {
            emit(Token("token"))
        }
    }

}
