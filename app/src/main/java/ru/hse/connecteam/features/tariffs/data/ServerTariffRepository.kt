package ru.hse.connecteam.features.tariffs.data

import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import javax.inject.Inject

class ServerTariffRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
) : TariffDataRepository {
}