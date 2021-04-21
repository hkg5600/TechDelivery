package com.example.core.di

import com.example.core.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoroutineDispatcherModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}