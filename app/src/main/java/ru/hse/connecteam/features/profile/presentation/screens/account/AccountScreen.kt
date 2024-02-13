package ru.hse.connecteam.features.profile.presentation.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import ru.hse.connecteam.features.profile.presentation.components.ExitButton
import ru.hse.connecteam.features.profile.presentation.components.OutlinedSettingsBaseButton
import ru.hse.connecteam.features.profile.presentation.components.TransparentAppBar
import ru.hse.connecteam.route.NavigationItem
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun AccountScreen(
    viewModel: AccountScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        topBar = { TransparentAppBar(title = "Аккаунт") }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 15.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                OutlinedSettingsBaseButton(text = viewModel.email, onClick = {
                    navController.navigate(NavigationItem.EmailChange.route)
                })
                OutlinedSettingsBaseButton(text = "Смена пароля", onClick = {
                    navController.navigate(NavigationItem.PasswordChange.route)
                })
            }
            ExitButton()
        }
    }
}

@Preview
@Composable
fun AccountScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //AccountScreen(navController = rememberNavController())
        }
    }
}