package ru.hse.connecteam.ui.components.modals

import android.view.Gravity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import kotlinx.coroutines.delay
import ru.hse.connecteam.ui.components.buttons.GradientFilledButton
import ru.hse.connecteam.ui.components.buttons.OutlinedGradientButton
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel

@Composable
fun SelfHidingBottomAlert(text: String) {
    Dialog(onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    brush = BaseGradientBrush,
                    shape = RoundedCornerShape(14.dp),
                )
                .padding(horizontal = 22.dp, vertical = 26.dp)
        ) {
            Text(text = text, style = OutlinedButtonLabel, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun SelfHidingBottomAlertPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                var shouldShowDialog by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(shouldShowDialog) {
                    if (shouldShowDialog) {
                        delay(1000L)
                        shouldShowDialog = false
                    }
                }
                //PasswordTextInput(onValueChanged = {})
                OutlinedGradientButton("Connecteam", onClick = {
                    shouldShowDialog = true
                })
                GradientFilledButton("Connecteam")
                OutlinedGradientButton("Connecteam", enabled = false)
                GradientFilledButton("Connecteam", enabled = false)
                if (shouldShowDialog) {
                    SelfHidingBottomAlert("Connecteam")
                }
            }
        }
    }
}