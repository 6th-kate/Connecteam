package ru.hse.connecteam.features.profile.domain

import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.shared.models.tariffs.TariffStatus
import java.util.Date

data class TariffDomainModel(
    val tariffInfo: TariffInfo,
    val endDate: Date?,
    val isMine: Boolean,
    val invitationCode: String,
    //val participants: List<TariffParticipant>?,
    val status: TariffStatus,
)