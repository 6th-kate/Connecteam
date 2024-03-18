package ru.hse.connecteam.shared.services.user

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import androidx.core.os.ConfigurationCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.shared.models.tariffs.TariffModel
import ru.hse.connecteam.shared.services.api.ApiClient
import ru.hse.connecteam.shared.services.api.TariffData
import ru.hse.connecteam.shared.services.datastore.AuthenticationService
import ru.hse.connecteam.shared.utils.STANDARD_BACKEND_DATE
import java.text.ParseException
import java.util.Date
import javax.inject.Inject

class TariffService @Inject constructor(
    private val authenticationService: AuthenticationService,
) {
    val tariff = MutableStateFlow<TariffModel?>(null)
    suspend fun forceUpdateTariffFlow() {
        withContext(Dispatchers.IO) {
            try {
                val token = authenticationService.getToken()
                val response = ApiClient.apiService.getUserTariff("Bearer $token")
                if (response == null || !response.isSuccessful || response.body() == null) {
                    if (response != null && response.code() == 401) {
                        authenticationService.onLogout()
                        tariff.value = null
                    } else {
                        tariff.value = null
                    }
                } else {
                    tariff.value = convert(response.body())
                }
            } catch (e: IllegalArgumentException) {
                authenticationService.onLogout()
                tariff.value = (null)
            }
        }
    }

    private fun convert(tariffData: TariffData?): TariffModel? {
        val tariffInfo = toTariffInfo(tariffData?.plan_type)
        if (tariffData == null || tariffInfo == null) {
            return null
        }
        return TariffModel(
            tariffInfo,
            endDate = toDateTime(tariffData.expiry_date),
            isMine = tariffData.holder_id == tariffData.user_id,
            participants = null,
            invitationCode = tariffData.invitation_code
            // TODO: ADD PARTICIPANTS CALL
        )
    }

    private fun toTariffInfo(tariffType: String?): TariffInfo? {
        return when (tariffType) {
            "basic" -> TariffInfo.SIMPLE
            "advanced" -> TariffInfo.ADVANCED
            "premium" -> TariffInfo.WIDE
            else -> null
        }
    }

    private fun toDateTime(time: String): Date? {
        return try {
            val currentLocale =
                ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
            SimpleDateFormat(STANDARD_BACKEND_DATE, currentLocale).parse(time)
        } catch (e: ParseException) {
            null
        }
    }
}