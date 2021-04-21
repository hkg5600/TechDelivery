package com.example.core.di

import com.example.core.data.session.CacheAccountDataSource
import com.example.core.data.session.DefaultSessionRepository
import com.example.core.data.session.FirebaseUserSession
import com.example.core.data.session.RemoteAccountDataSource
import com.example.core.domain.session.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserSessionRepository(
        firebaseUserSession: FirebaseUserSession,
        remoteAccountDataSource: RemoteAccountDataSource,
        cacheAccountDataSource: CacheAccountDataSource
    ): SessionRepository {
        return DefaultSessionRepository(
            firebaseUserSession,
            remoteAccountDataSource,
            cacheAccountDataSource
        )
    }
}