package ru.hse.connecteam.features.tariffs.domain

import kotlinx.coroutines.flow.Flow
import ru.hse.connecteam.shared.models.response.ResponseInfo
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.shared.models.tariffs.TariffModel

interface TariffDataRepository {
    suspend fun getTariffFlow(): Flow<TariffModel?>
    suspend fun purchasePlan(tariffInfo: TariffInfo): ResponseInfo
}