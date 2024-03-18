package ru.hse.connecteam.shared.models.tariffs

import java.util.Date

data class TariffModel(
    val tariffInfo: TariffInfo,
    val invitationCode: String,
    val endDate: Date?,
    val isMine: Boolean,
    val participants: List<TariffParticipant>?
)