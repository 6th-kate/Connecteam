package ru.hse.connecteam.features.profile.presentation.screens.access

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import ru.hse.connecteam.shared.models.TariffInfo
import ru.hse.connecteam.shared.models.TariffModel
import java.util.Locale

class MyTariffViewModel(tariffModel: TariffModel?) : ViewModel() {
    val hasTariff: Boolean = tariffModel != null
    val tariffInfo: TariffInfo? = tariffModel?.tariffInfo
    val hasParticipants: Boolean = !tariffModel?.participants.isNullOrEmpty()
    val isMyTariff: Boolean? = tariffModel?.isMine
    val endDate: String =
        if (tariffModel != null)
            SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(
                tariffModel.endDate
            ) else ""
}