package ru.hse.connecteam.features.profile.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import ru.hse.connecteam.features.profile.presentation.components.OutlinedSettingsBaseButton
import ru.hse.connecteam.ui.components.bars.TransparentAppBar
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.components.images.AvatarPicker
import ru.hse.connecteam.ui.theme.BigWhiteLabel
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(), navController: NavController
) {
    Scaffold(topBar = {
        TransparentAppBar(
            title = "", navController = navController
        )
    }) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AvatarPicker(image = viewModel.image, enabled = false)
            Text(text = viewModel.fullName, style = BigWhiteLabel)
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedSettingsBaseButton(text = "Аккаунт", onClick = {
                navController.navigate(NavigationItem.Account.route)
            })
            OutlinedSettingsBaseButton(text = "Личные данные", onClick = {
                navController.navigate(NavigationItem.Personal.route)
            })
            OutlinedSettingsBaseButton(text = "Доступ", onClick = {
                navController.navigate(NavigationItem.MyTariff.route)
            })
            OutlinedSettingsBaseButton(text = "Компания", onClick = {
                navController.navigate(NavigationItem.Company.route)
            })
            OutlinedSettingsBaseButton(text = "Расширенные настройки", onClick = {})
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //ProfileScreen(navController = rememberNavController())
        }
    }
}