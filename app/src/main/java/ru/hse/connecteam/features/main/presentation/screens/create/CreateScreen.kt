package ru.hse.connecteam.features.main.presentation.screens.create

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import ru.hse.connecteam.features.main.presentation.components.DateField
import ru.hse.connecteam.features.tariffs.presentation.components.PayWall
import ru.hse.connecteam.ui.components.bars.BottomNavBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.ShareButtons
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.modals.PickDateDialog
import ru.hse.connecteam.ui.components.modals.SelfHidingBottomAlert
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.BaseGrayBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.MediumGrayLabel
import ru.hse.connecteam.ui.theme.MediumWhiteLabel

@Composable
fun CreateScreen(
    navController: NavController,
    viewModel: CreateViewModel = hiltViewModel()
) {
    Scaffold(bottomBar = { BottomNavBar(navController) }) { paddingValues ->
        LaunchedEffect(viewModel.shouldShowAlert) {
            if (viewModel.shouldShowAlert) {
                delay(1000L)
                viewModel.stopAlert()
            }
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(all = 15.dp)
                    .border(
                        width = 1.dp,
                        brush = if (viewModel.hasTariff) BaseGradientBrush else BaseGrayBrush,
                        shape = RoundedCornerShape(16.dp),
                    )
                    .padding(horizontal = 5.dp, vertical = 15.dp)
            ) {
                Text(
                    text = "Создать игру",
                    style = if (viewModel.hasTariff) MediumWhiteLabel else MediumGrayLabel,
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                )
                BaseOutlinedTextInput(
                    text = viewModel.nameValue,
                    onValueChanged = { viewModel.updateName(it) },
                    enabled = !viewModel.inputDisabled,
                    label = "Имя",
                    readOnly = true // TODO(ping Natasha, make this field changeable)
                )
                DateField(
                    text = viewModel.dateValue,
                    inputEnabled = !viewModel.inputDisabled,
                    onDatePickOpen = { viewModel.openDatePicker() })
                BaseOutlinedTextInput(
                    text = viewModel.gameTitleValue,
                    onValueChanged = { viewModel.updateGameTitle(it) },
                    enabled = !viewModel.inputDisabled,
                    label = "Название игры"
                )
                if (!viewModel.created) {
                    GradientFilledButton(
                        text = "Создать",
                        onClick = { viewModel.onCreate() },
                        enabled = viewModel.createEnabled
                    )
                } else {
                    ShareButtons(subject = viewModel.subject, copyText = viewModel.linkText)
                }
                if (viewModel.showDatePicker) {
                    PickDateDialog(
                        onDateSelected = { viewModel.updateDate(it) },
                        onStandardDateSelected = { viewModel.updateDate(it) },
                        onDismiss = { viewModel.hideDatePicker() }
                    )
                }
            }
            if (!viewModel.hasTariff) {
                PayWall(
                    navController = navController,
                    message = "Эта функция доступна только пользователям с активным тарифом"
                )
            }
            if (viewModel.shouldShowAlert) {
                SelfHidingBottomAlert(viewModel.alertText)
            }
        }
    }
}

@Preview
@Composable
fun CreateScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            CreateScreen(
                navController = rememberNavController()
            )
        }
    }
}
