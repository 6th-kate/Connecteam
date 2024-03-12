package ru.hse.connecteam.features.auth.presentation.screens.loading

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
            "parse code and nav to screen" +
                    "if code is valid:" +
                    "nav to appropriate screen of invite check auth/unauth"
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            LoadingAnimation()
        }
    }
}