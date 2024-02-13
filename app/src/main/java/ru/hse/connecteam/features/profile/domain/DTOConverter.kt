package ru.hse.connecteam.features.profile.domain

import ru.hse.connecteam.shared.models.TariffInfo
import ru.hse.connecteam.shared.models.TariffModel
import ru.hse.connecteam.shared.services.api.TariffData
import ru.hse.connecteam.shared.services.api.UserData
import java.time.LocalDateTime

class DTOConverter {
    companion object {
        fun convert(userData: UserData?): UserDomainModel? {
            if (userData == null) {
                return null
            }
            return UserDomainModel(
                userData.first_name,
                userData.second_name,
                null,
                userData.email,
                userData.description,
                null,
                userData.company_name,
                userData.company_url,
                userData.company_info
            )
        }

        fun convert(tariffData: TariffData?): TariffModel? {
            if (tariffData == null || toTariffInfo(tariffData.plan_type) == null) {
                return null
            }
            return null
        }

        private fun toTariffInfo(tariffType: String): TariffInfo? {
            return when (tariffType) {
                "basic" -> TariffInfo.SIMPLE
                "advanced" -> TariffInfo.ADVANCED
                "premium" -> TariffInfo.WIDE
                else -> null
            }
        }
    }
}