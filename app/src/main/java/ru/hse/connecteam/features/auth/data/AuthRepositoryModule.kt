package ru.hse.connecteam.features.auth.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.hse.connecteam.features.auth.domain.AuthRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: ServerAuthRepository
    ): AuthRepository
}