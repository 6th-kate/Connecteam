package ru.hse.connecteam.features.gamelist.presentation.screens.create

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.hse.connecteam.features.auth.presentation.screens.signup.SignUpScreen
import ru.hse.connecteam.features.gamelist.presentation.components.DateField
import ru.hse.connecteam.ui.components.bottombar.BottomNavBar
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.ShareButtons
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.modals.PickDateDialog
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.MediumWhiteLabel

@Composable
fun CreateScreen(
    navController: NavController,
    viewModel: CreateViewModel,// = hiltViewModel()
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
                brush = BaseGradientBrush,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(horizontal = 5.dp, vertical = 15.dp)
    ) {
        Text(
            text = "Создать игру",
            style = MediumWhiteLabel,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        )
        BaseOutlinedTextInput(
            text = viewModel.nameValue,
            onValueChanged = { viewModel.updateName(it) },
            enabled = !viewModel.created,
            label = "Имя"
        )
        DateField(
            text = viewModel.dateValue,
            inputEnabled = !viewModel.created,
            onDatePickOpen = { viewModel.openDatePicker() })
        BaseOutlinedTextInput(
            text = viewModel.gameTitleValue,
            onValueChanged = { viewModel.updateGameTitle(it) },
            enabled = !viewModel.created,
            label = "Название игры"
        )
        if (!viewModel.created) {
            GradientFilledButton(text = "Создать", enabled = viewModel.createEnabled)
        } else {
            ShareButtons(subject = "", copyText = "")
        }
        if (viewModel.showDatePicker) {
            PickDateDialog(
                onDateSelected = { viewModel.updateDate(it) },
                onDismiss = { viewModel.hideDatePicker() }
            )
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun CreateScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold(bottomBar = { BottomNavBar() }) {
                CreateScreen(
                    viewModel = CreateViewModel(),
                    navController = rememberNavController()
                )
            }
        }
    }
}
