package ru.hse.connecteam.shared.models.tariffs

import java.util.Date

data class TariffModel(
    val tariffInfo: TariffInfo,
    val cost: Int,
    val endDate: Date,
    val isMine: Boolean,
    val participants: List<TariffParticipant>?
)