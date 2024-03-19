package ru.hse.connecteam.features.tariffs.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository
import ru.hse.connecteam.shared.models.response.ResponseInfo
import ru.hse.connecteam.shared.models.response.StatusInfo
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.shared.models.tariffs.TariffModel
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.TariffRequest
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.services.user.TariffService
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ServerTariffRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val tariffService: TariffService,
) : TariffDataRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getTariffFlow(): Flow<TariffModel?> {
        tariffService.forceUpdateTariffFlow()
        return tariffService.tariff.flatMapLatest { value: TariffModel? ->
            flow { emit(value) }
        }
    }

    override suspend fun purchasePlan(tariffInfo: TariffInfo): ResponseInfo =
        suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.purchasePlan(
                    "Bearer $token",
                    TariffRequest(30, tariffInfo.jsonName)
                )
                launch(Dispatchers.Main) {
                    val gameList = response?.body()
                    if (response == null || !response.isSuccessful || gameList == null) {
                        if (response != null && response.code() == 401) {
                            authenticationService.onLogout()
                        }
                        withContext(Dispatchers.Main) {
                            continuation.resume(ResponseInfo(StatusInfo.ERROR))
                        }
                    } else {
                        continuation.resume(ResponseInfo(StatusInfo.OK))
                    }
                }
            }
        }
}