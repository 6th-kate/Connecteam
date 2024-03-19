package ru.hse.connecteam.features.main.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.hse.connecteam.features.main.domain.GameStaticRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class GameStaticDataModule {
    @Binds
    abstract fun bindGameStaticRepository(
        gameStaticRepositoryImpl: GameStaticRepositoryImpl
    ): GameStaticRepository
}