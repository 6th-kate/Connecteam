package ru.hse.connecteam.features.profile.presentation.screens.access.participants

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.hse.connecteam.features.tariffs.domain.TariffDataRepository
import ru.hse.connecteam.shared.models.tariffs.TariffParticipant
import javax.inject.Inject

@HiltViewModel
class TariffParticipantsViewModel @Inject constructor(
    private val repository: TariffDataRepository
) : ViewModel() {
    class ParticipantViewModel(val fullName: String, val email: String, val modelIndex: Int)

    private var initialized: Boolean = false
    var showError by mutableStateOf(false)
        private set
    var errorText by mutableStateOf("Ошибка")
        private set

    var canAddMoreParticipants by mutableStateOf(false)
        private set

    private var participantModels: List<TariffParticipant> = mutableStateListOf()

    init {
        if (!initialized) {
            //TODO(add server)
            participantModels = listOf(
                TariffParticipant("PLayer", "Playerson1", "player_playerson1@mail.ru"),
                TariffParticipant("PLayer", "Playerson2", "player_playerson1@mail.ru"),
                TariffParticipant("PLayer", "Playerson3", "player_playerson1@mail.ru"),
            )
            //if (got error)
            //errorText = "Не удалось загрузить участников тарифа"
            //showError = true
            //canAddMoreParticipants = participantModels.count() >= maxAvailable
            initialized = true
        }
    }

    val participants: List<ParticipantViewModel> =
        participantModels.mapIndexed { index, tariffParticipant ->
            ParticipantViewModel(
                "${tariffParticipant.name} ${tariffParticipant.surname}",
                tariffParticipant.email,
                index
            )
        }

    var showDeleteBottomSheet by mutableStateOf(false)
        private set
    var showAddBottomSheet by mutableStateOf(false)
        private set

    val subject: String = "Подключение к тарифу"
    private val linkText: String =
        "Подключитесь к игре по ссылке https://github.com/6th-kate"

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
            //TODO("repository.deleteParticipant(participantModels[selectedParticipant!!.modelIndex])") // TODO(trycatch)
        }
    }

    fun addParticipant() {
        selectedParticipant = null
        showDeleteBottomSheet = false
        showAddBottomSheet = true
        //TODO("repository.generateInvitation()") // TODO(trycatch)
    }

    fun hideAddParticipantDialog() {
        showAddBottomSheet = false
    }

    fun hideDeleteParticipantDialog() {
        selectedParticipant = null
        showDeleteBottomSheet = false
    }
}