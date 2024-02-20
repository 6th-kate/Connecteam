package ru.hse.connecteam.features.tariffs.presentation.tarifflist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import javax.inject.Inject

@HiltViewModel
class TariffListViewModel @Inject constructor() : ViewModel() {
    var selectedTariff by mutableStateOf(TariffInfo.SIMPLE)
        private set

    fun selectTariff(tariffInfo: TariffInfo) {
        selectedTariff = tariffInfo
    }

    val tariffs: List<TariffInfo> = listOf(
        TariffInfo.SIMPLE,
        TariffInfo.ADVANCED,
        TariffInfo.WIDE,
    )

    fun getPathParameters(): String {
        return selectedTariff.name
    }
}