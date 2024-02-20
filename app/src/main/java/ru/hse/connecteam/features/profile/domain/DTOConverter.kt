package ru.hse.connecteam.features.profile.domain

import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import androidx.compose.ui.text.toLowerCase
import androidx.core.os.ConfigurationCompat
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.shared.models.user.UserModel
import ru.hse.connecteam.shared.services.api.TariffData
import ru.hse.connecteam.shared.services.api.UserData
import java.text.ParseException
import java.util.Date
import java.util.Locale


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

        fun convert(userData: UserModel?): UserDomainModel? {
            if (userData == null) {
                return null
            }
            return UserDomainModel(
                userData.firstName,
                userData.surname,
                null,
                userData.email,
                userData.about,
                null,
                userData.companyName,
                userData.companySite,
                userData.companyAbout
            )
        }

        fun convert(tariffData: TariffData?): TariffDomainModel? {
            val tariffInfo = toTariffInfo(tariffData?.plan_type)
            if (tariffData == null || tariffInfo == null) {
                return null
            }
            return TariffDomainModel(
                tariffInfo,
                toDateTime(tariffData.expiry_date),
                isMine = tariffData.holder_id == tariffData.user_id,
                null,
                confirmed = tariffData.confirmed.lowercase().contains("true")
            )
            // TODO("add participants")
        }

        private fun toDateTime(time: String): Date? {
            return try {
                val currentLocale =
                    ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", currentLocale).parse(time)
            } catch (e: ParseException) {
                null
            }
        }

        private fun toTariffInfo(tariffType: String?): TariffInfo? {
            return when (tariffType) {
                "basic" -> TariffInfo.SIMPLE
                "advanced" -> TariffInfo.ADVANCED
                "premium" -> TariffInfo.WIDE
                else -> null
            }
        }
    }
}