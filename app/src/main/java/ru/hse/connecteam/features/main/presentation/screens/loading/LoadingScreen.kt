package ru.hse.connecteam.features.main.presentation.screens.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ru.hse.connecteam.ui.components.animated.LoadingAnimation

@Composable
fun LoadingScreen(
    navController: NavController,
    inviteCode: String
) {
    LaunchedEffect(true) {
        TODO(
            "loading" +
                    "if game was in process" +
                    "if an invite was pending" +
                    "open them and main otherwise"
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            LoadingAnimation()
        }
    }
}