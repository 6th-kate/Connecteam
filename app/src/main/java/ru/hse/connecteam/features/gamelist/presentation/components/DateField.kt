package ru.hse.connecteam.features.gamelist.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.modals.PickDateDialog

@Composable
fun DateField(
    text: String,
    inputEnabled: Boolean,
    onTextUpdate: (String) -> Unit = { },
    onDatePickOpen: () -> Unit = { },
) {
    BaseOutlinedTextInput(
        text = text,
        onValueChanged = { onTextUpdate(it) },
        label = "Дата",
        enabled = inputEnabled,
        trailingIcon = {
            IconButton(onClick = { onDatePickOpen() }) {
                GradientIcon(image = Icons.Rounded.CalendarMonth)
            }
        }
    )
}