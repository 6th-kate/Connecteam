package ru.hse.connecteam.features.profile.presentation.screens.access

import android.icu.text.SimpleDateFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.features.profile.domain.TariffDomainModel
import ru.hse.connecteam.shared.models.tariffs.TariffInfo
import ru.hse.connecteam.shared.models.tariffs.TariffStatus
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MyTariffViewModel @Inject constructor(
    private val repository: ProfileDataRepository,
) : ViewModel() {
    private var initialized: Boolean by mutableStateOf(false)
    private var tariffModel: TariffDomainModel? by mutableStateOf(null)
    private var myID by mutableStateOf("")

    val hasTariff: Boolean
        get() = tariffModel != null

    val tariffStatus: TariffStatus?
        get() = tariffModel?.status
    val tariffInfo: TariffInfo?
        get() = tariffModel?.tariffInfo
    val hasParticipants: Boolean
        get() = tariffModel?.tariffInfo?.hasParticipants == true
    val isMyTariff: Boolean?
        get() = tariffModel?.isMine
    val endDate: String
        get() =
            if (tariffModel != null)
                SimpleDateFormat(
                    "dd.MM.yyyy",
                    Locale.getDefault()
                ).format(
                    tariffModel?.endDate
                ) else ""


    init {
        if (!initialized) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.getTariffFlow().collectLatest { tariff ->
                    if (tariff != null) {
                        tariffModel = TariffDomainModel(
                            tariff.tariffInfo,
                            tariff.endDate,
                            tariff.isMine,
                            tariff.invitationCode,
                            tariff.status
                        )
                        initialized = true
                    }
                }
                repository.getUserFlow().collectLatest { user ->
                    if (user != null) {
                        myID = user.id
                    }
                }
            }
        }
    }

    fun onDeleteFromTariff() {
        // TODO("fix this")
        /*if (myID.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.deleteParticipant(myID)
                if (response.status == StatusInfo.OK) {
                    withContext(Dispatchers.Main) {
                        if (response.isNullOrEmpty() || participants.contains(null)) {
                            errorText = "Не удалось загрузить участников тарифа"
                            showError = true
                        } else {
                            participantModels = participants as List<TariffParticipant>
                            linkText =
                                "Пользователь $userFullName приглашает Вас " +
                                        "присоединиться к тарифу Расширенный. " +
                                        "Для подключения используйте ссылку " +
                                        "https://connecteam.ru/invite/plan/${inviteCode}"
                        }
                    }
                } else if (response.status == StatusInfo.ERROR) {
                    errorText = "Не удалось загрузить участников тарифа"
                    showError = true
                }
            }
        }*/
    }
}