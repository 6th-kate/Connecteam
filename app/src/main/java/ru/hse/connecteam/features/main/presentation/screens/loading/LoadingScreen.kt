package ru.hse.connecteam.features.main.presentation.screens.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.connecteam.ui.components.animated.LoadingAnimation

@Composable
fun LoadingScreen(
    navController: NavController,
    viewModel: LoadingViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.shouldMove) {
        if (viewModel.shouldMove) {
            navController.navigate(viewModel.movePath) {
                popUpTo(0)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            LoadingAnimation()
        }
    }
}