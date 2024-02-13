package ru.hse.connecteam.features.profile.presentation.screens.company

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataScreen

@Composable
fun CompanyDataScreen(viewModel: CompanyDataViewModel = hiltViewModel()) {
    GenericDataScreen(viewModel = viewModel)
}