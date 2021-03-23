package com.example.domain.di

import com.example.domain.utils.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(ApplicationComponent::class)
@Module
class CoroutineDispatcherModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}