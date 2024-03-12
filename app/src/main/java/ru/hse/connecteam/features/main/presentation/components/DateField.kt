package ru.hse.connecteam.features.main.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

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
        readOnly = true,
        enabled = inputEnabled,
        trailingIcon = {
            if (inputEnabled) {
                IconButton(onClick = { onDatePickOpen() }) {
                    GradientIcon(image = Icons.Rounded.CalendarMonth)
                }
            } else {
                Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    contentDescription = "Календарь",
                    tint = WhiteSemiTransparent25
                )
            }
        }
    )
}