package ru.hse.connecteam.features.profile.presentation.screens.access.participants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.buttons.ShareButtons
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.MediumWhiteLabel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddParticipantBottomSheet(viewModel: TariffParticipantsViewModel) {
    val addSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { viewModel.hideAddParticipantDialog() },
        sheetState = addSheetState
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            if (viewModel.canAddMoreParticipants) {
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 0.dp),
                    text = "Пригласите участника по ссылке:",
                    style = MediumWhiteLabel,
                )
                ShareButtons(
                    subject = viewModel.subject,
                    copyText = viewModel.getCopyText()
                )
            } else {
                Text(
                    text = "В тариф больше нельзя добавить участников",
                    style = BigWhiteLabel,
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}