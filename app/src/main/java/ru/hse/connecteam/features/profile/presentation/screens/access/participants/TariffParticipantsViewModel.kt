package ru.hse.connecteam.features.profile.presentation.screens.access.participants

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.connecteam.features.profile.domain.ProfileDataRepository
import ru.hse.connecteam.shared.models.response.StatusInfo
import ru.hse.connecteam.shared.models.tariffs.TariffParticipant
import javax.inject.Inject

@HiltViewModel
class TariffParticipantsViewModel @Inject constructor(
    private val repository: ProfileDataRepository
) : ViewModel() {
    class ParticipantViewModel(val fullName: String, val email: String, val id: String)

    private var initialized: Boolean = false
    var showError by mutableStateOf(false)
        private set
    var errorText by mutableStateOf("Ошибка")
        private set
    private var userFullName by mutableStateOf("")

    val subject: String = "Подключение к тарифу"
    private var linkText: String by mutableStateOf("")

    var canAddMoreParticipants by mutableStateOf(false)
        private set

    private var participantModels: List<TariffParticipant> = mutableStateListOf()

    private var inviteCode by mutableStateOf("")

    init {
        if (!initialized) {
            viewModelScope.launch {
                repository.getUserFlow().collectLatest { user ->
                    if (user == null) {
                        errorText = "Не удалось загрузить участников тарифа"
                        showError = true
                    } else {
                        userFullName = "${user.firstName} ${user.firstName}"
                    }
                }
                repository.getTariffFlow().collectLatest { tariff ->
                    if (tariff == null) {
                        errorText = "Не удалось загрузить участников тарифа"
                        showError = true
                    } else {
                        inviteCode = tariff.invitationCode
                        val participants = repository.getTariffParticipants(inviteCode)
                        if (participants.isNullOrEmpty() || participants.contains(null)) {
                            errorText = "Не удалось загрузить участников тарифа"
                            showError = true
                        } else {
                            participantModels = participants as List<TariffParticipant>
                            linkText =
                                "Пользователь $userFullName приглашает Вас " +
                                        "присоединиться к тарифу Расширенный. " +
                                        "Для подключения используйте ссылку " +
                                        "https://connecteam.ru/invite/plan/${tariff.invitationCode}"
                        }
                    }
                }
                initialized = true
            }
        }
    }

    val participants: List<ParticipantViewModel> =
        participantModels.map { tariffParticipant ->
            ParticipantViewModel(
                "${tariffParticipant.name} ${tariffParticipant.surname}",
                tariffParticipant.email,
                tariffParticipant.userId
            )
        }

    var showDeleteBottomSheet by mutableStateOf(false)
        private set
    var showAddBottomSheet by mutableStateOf(false)
        private set

    fun getCopyText(): String {
        return linkText
    }

    var selectedParticipant: ParticipantViewModel? by mutableStateOf(null)

    fun showDeleteParticipantDialog(selected: ParticipantViewModel) {
        selectedParticipant = selected
        showDeleteBottomSheet = true
        showAddBottomSheet = false
    }

    fun deleteSelectedParticipant() {
        if (selectedParticipant != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val id = selectedParticipant?.id
                if (id != null) {
                    val response = repository.deleteParticipant(id)
                    if (response.status == StatusInfo.OK) {
                        val participants = repository.getTariffParticipants(inviteCode)
                        withContext(Dispatchers.Main) {
                            if (participants.isNullOrEmpty() || participants.contains(null)) {
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
            }
        }
    }

    fun addParticipant() {
        selectedParticipant = null
        showDeleteBottomSheet = false
        showAddBottomSheet = true
    }

    fun hideAddParticipantDialog() {
        showAddBottomSheet = false
    }

    fun hideDeleteParticipantDialog() {
        selectedParticipant = null
        showDeleteBottomSheet = false
    }
}