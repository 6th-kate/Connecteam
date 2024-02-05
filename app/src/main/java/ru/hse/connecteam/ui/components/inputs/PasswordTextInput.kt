package ru.hse.connecteam.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun PasswordTextInput(
    text: String,
    onValueChanged: (String) -> Unit,
    label: String? = null,
    enabled: Boolean = true,
    error: String = "",
    isError: Boolean = false,
    hasSmallClickable: Boolean = false,
    smallClickableText: String = "",
    smallClickableClick: () -> Unit = { }
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    BaseOutlinedTextInput(
        text = text,
        onValueChanged = onValueChanged,
        label = label,
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        error = error,
        enabled = enabled,
        isError = isError,
        hasSmallClickable = hasSmallClickable,
        smallClickableText = smallClickableText,
        smallClickableClick = smallClickableClick,
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.VisibilityOff
            else Icons.Filled.Visibility

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Скрыть" else "Показать"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                GradientIcon(image = image, description = description)
            }
        }
    )
}

@Preview
@Composable
fun PasswordTextInputPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val text by remember {
                mutableStateOf("")
            }
            PasswordTextInput(text, {}, "Пароль")
        }
    }
}