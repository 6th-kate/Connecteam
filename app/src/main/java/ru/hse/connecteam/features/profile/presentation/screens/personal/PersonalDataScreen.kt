package ru.hse.connecteam.features.profile.presentation.screens.personal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataScreen
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun PersonalDataScreen(
    viewModel: PersonalDataViewModel = hiltViewModel(),
) {
   GenericDataScreen(viewModel = viewModel)
}

@Preview
@Composable
fun PersonalDataScreenPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //PersonalDataScreen(navController = rememberNavController())
        }
    }
}