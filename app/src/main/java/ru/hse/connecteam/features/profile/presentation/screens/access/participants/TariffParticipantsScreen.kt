package ru.hse.connecteam.features.profile.presentation.screens.access.participants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.connecteam.features.profile.presentation.components.OutlinedSettingsBaseButton
import ru.hse.connecteam.features.profile.presentation.components.TransparentAppBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.components.buttons.ShareButtons
import ru.hse.connecteam.ui.theme.BigWhiteLabel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TariffParticipantsScreen(
    viewModel: TariffParticipantsViewModel = hiltViewModel(),
    navController: NavController
) {
    val deleteSheetState = rememberModalBottomSheetState()
    val addSheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = { TransparentAppBar(title = "Участники тарифа", navController = navController) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 0.dp, vertical = 15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .weight(weight = 1f, fill = false)
            ) {
                viewModel.participants.forEach { participant ->
                    OutlinedSettingsBaseButton(
                        text = participant.fullName,
                        topLabel = participant.email,
                        onClick = { viewModel.showDeleteParticipantDialog(participant) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            GradientFilledButton(text = "Добавить участника")
            if (viewModel.showDeleteBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.hideDeleteParticipantDialog() },
                    sheetState = deleteSheetState
                ) {
                    GradientFilledButton(
                        text = "Удалить участника ${viewModel.selectedParticipant?.fullName}",
                        onClick = {
                            viewModel.deleteSelectedParticipant()
                            viewModel.hideDeleteParticipantDialog()
                        }
                    )
                    OutlinedGradientButton(
                        text = "Отмена",
                        onClick = { viewModel.hideDeleteParticipantDialog() }
                    )
                }
            }
            if (viewModel.showAddBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { viewModel.hideAddParticipantDialog() },
                    sheetState = addSheetState
                ) {
                    if (viewModel.canAddMoreParticipants) {
                        Text(
                            text = "В тариф больше нельзя добавить участников",
                            style = BigWhiteLabel,
                        )
                        ShareButtons(
                            subject = viewModel.subject,
                            copyText = viewModel.getCopyText()
                        )
                        OutlinedGradientButton(
                            text = "Отмена",
                            onClick = { viewModel.hideAddParticipantDialog() }
                        )
                    } else {
                        Text(
                            text = "В тариф больше нельзя добавить участников",
                            style = BigWhiteLabel,
                        )
                    }
                }
            }
        }
    }
}