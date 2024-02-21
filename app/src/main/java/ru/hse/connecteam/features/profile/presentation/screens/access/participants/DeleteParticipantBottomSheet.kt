package ru.hse.connecteam.features.profile.presentation.screens.access.participants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteParticipantBottomSheet(viewModel: TariffParticipantsViewModel) {
    val deleteSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { viewModel.hideDeleteParticipantDialog() },
        sheetState = deleteSheetState
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
        ) {
            GradientFilledButton(
                text = "Удалить участника \n${viewModel.selectedParticipant?.fullName}",
                onClick = {
                    viewModel.deleteSelectedParticipant()
                    viewModel.hideDeleteParticipantDialog()
                }
            )
            OutlinedGradientButton(
                text = "Отмена",
                onClick = { viewModel.hideDeleteParticipantDialog() }
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}