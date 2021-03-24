package com.example.data.di

import com.example.data.session.DefaultSessionRepository
import com.example.data.session.FirebaseUserSession
import com.example.domain.session.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserSessionRepository(dataSource: FirebaseUserSession) : SessionRepository {
        return DefaultSessionRepository(dataSource)
    }
}