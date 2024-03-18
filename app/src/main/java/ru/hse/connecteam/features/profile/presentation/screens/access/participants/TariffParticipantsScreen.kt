package ru.hse.connecteam.features.profile.presentation.screens.access.participants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.hse.connecteam.features.profile.presentation.components.OutlinedSettingsBaseButton
import ru.hse.connecteam.ui.components.bars.TransparentAppBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun TariffParticipantsScreen(
    viewModel: TariffParticipantsViewModel = hiltViewModel(),
    navController: NavController
) {
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
            ) {
                viewModel.participants.forEach { participant ->
                    OutlinedSettingsBaseButton(
                        text = participant.fullName,
                        topLabel = participant.email,
                        onClick = { viewModel.showDeleteParticipantDialog(participant) }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f)
            )
            if (viewModel.canAddMoreParticipants) {
                GradientFilledButton(
                    text = "Добавить участника",
                    onClick = { viewModel.addParticipant() })
            } else {
                GradientFilledButton(
                    enabled = false,
                    text = "Достигнуто максимальное число участников"
                )
            }
        }
        if (viewModel.showDeleteBottomSheet) {
            DeleteParticipantBottomSheet(viewModel)
        }
        if (viewModel.showAddBottomSheet) {
            AddParticipantBottomSheet(viewModel)
        }
        if (viewModel.showError) {
            SelfHidingBottomAlert(viewModel.errorText)
        }
    }
}


@Preview
@Composable
fun TariffParticipantsScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            TariffParticipantsScreen(navController = rememberNavController())
        }
    }
}