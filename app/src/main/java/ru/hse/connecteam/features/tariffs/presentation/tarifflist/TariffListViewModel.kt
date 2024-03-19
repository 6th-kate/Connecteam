package ru.hse.connecteam.features.tariffs.presentation.tarifflist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import javax.inject.Inject

@HiltViewModel
class TariffListViewModel @Inject constructor(
    private val repository: TariffDataRepository,
) : ViewModel() {
    var selectedTariff by mutableStateOf(TariffInfo.SIMPLE)
        private set
    var alreadyHasTariff by mutableStateOf(false)
        private set

    private var initialized by mutableStateOf(false)

    init {
        if (!initialized) {
            viewModelScope.launch {
                repository.getTariffFlow().collectLatest { tariff ->
                    alreadyHasTariff = tariff != null
                    if (tariff != null) {
                        selectedTariff = tariff.tariffInfo
                    }
                }
            }
            initialized = true
        }
    }

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