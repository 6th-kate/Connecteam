package ru.hse.connecteam.features.profile.presentation.screens.access

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.models.TariffInfo
import ru.hse.connecteam.shared.models.TariffModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyTariffViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : ViewModel() {
    private var initialized: Boolean = false
    private var tariffModel: TariffModel? = null

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
    val tariffInfo: TariffInfo? = tariffModel?.tariffInfo
    val hasParticipants: Boolean = !tariffModel?.participants.isNullOrEmpty()
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