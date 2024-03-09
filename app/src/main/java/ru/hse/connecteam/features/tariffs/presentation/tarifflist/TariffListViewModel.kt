package ru.hse.connecteam.features.tariffs.presentation.tarifflist

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
class TariffListViewModel @Inject constructor(
    private val repository: TariffDataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var selectedTariff by mutableStateOf(TariffInfo.SIMPLE)
        private set

    val alreadyHasTariff = savedStateHandle.get<Boolean>("hasTariff") ?: false

    fun selectTariff(tariffInfo: TariffInfo) {
        selectedTariff = tariffInfo
    }

    val tariffs: List<TariffInfo> = listOf(
        TariffInfo.SIMPLE,
        TariffInfo.ADVANCED,
        TariffInfo.WIDE,
    )

    fun discontinueTariff() {
        //TODO(repository.cancelTariff())
    }

    fun getPathParameters(): String {
        return selectedTariff.name
    }
}