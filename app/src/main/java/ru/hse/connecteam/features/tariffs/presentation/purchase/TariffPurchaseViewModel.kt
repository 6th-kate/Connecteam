package ru.hse.connecteam.features.tariffs.presentation.purchase

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import javax.inject.Inject

@HiltViewModel
class TariffPurchaseViewModel @Inject constructor(
    private val repository: TariffDataRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val tariffName = savedStateHandle.get<String>("tariff").orEmpty()
    var shouldReturn by mutableStateOf(false)
        private set
    var purchaseButtonText by mutableStateOf("Купить")
        private set
    var purchaseButtonEnabled by mutableStateOf(true)
        private set

    val tariffInfo: TariffInfo = try {
        TariffInfo.valueOf(tariffName)
    } catch (e: IllegalArgumentException) {
        shouldReturn = true
        TariffInfo.SIMPLE
    }

    fun purchase() {
        if (purchaseButtonEnabled) {
            purchaseButtonEnabled = false
            purchaseButtonText = "Отправляем заявку..."

        }
    }
}