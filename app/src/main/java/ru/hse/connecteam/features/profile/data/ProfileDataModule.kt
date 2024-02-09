package ru.hse.connecteam.features.profile.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository


@Module
@InstallIn(ActivityComponent::class)
abstract class ProfileDataModule {
    @Binds
    abstract fun bindAuthRepository(
        profileRepositoryImpl: ServerProfileRepository
    ): ProfileDataRepository
}