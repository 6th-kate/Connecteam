package ru.hse.connecteam.features.tariffs.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository

@Module
@InstallIn(ActivityComponent::class)
abstract class TariffDataModule {
    @Binds
    abstract fun bindTariffRepository(
        tariffRepositoryImpl: ServerTariffRepository
    ): TariffDataRepository
}