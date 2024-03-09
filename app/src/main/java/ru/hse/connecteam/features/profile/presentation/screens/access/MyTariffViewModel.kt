package ru.hse.connecteam.features.profile.presentation.screens.access

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.domain.TariffDomainModel
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyTariffViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : ViewModel() {
    private var initialized: Boolean = false
    private var tariffModel: TariffDomainModel? = null

    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                val tariff = repository.getTariff()
                CoroutineScope(Dispatchers.Main).launch {
                    tariffModel = tariff
                    initialized = true
                }
            }
        }
    }

    val hasTariff: Boolean = tariffModel != null
    val confirmed: Boolean = tariffModel?.confirmed == true
    val tariffInfo: TariffInfo? = tariffModel?.tariffInfo
    val hasParticipants: Boolean = tariffModel?.tariffInfo?.hasParticipants == true
    val isMyTariff: Boolean? = tariffModel?.isMine
    val endDate: String =
        if (tariffModel != null)
            SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(
                tariffModel?.endDate
            ) else ""
}