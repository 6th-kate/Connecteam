package ru.hse.connecteam.features.profile.presentation.screens.company

import androidx.compose.runtime.Composable
import ru.hse.connecteam.features.profile.presentation.components.datascreen.GenericDataScreen

@Composable
fun CompanyDataScreen(viewModel:CompanyDataViewModel){
    GenericDataScreen(viewModel = viewModel)
}