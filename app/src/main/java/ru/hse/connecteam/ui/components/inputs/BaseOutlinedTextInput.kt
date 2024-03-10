package ru.hse.connecteam.ui.components.inputs

import android.text.TextUtils
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.buttons.SmallClickableText
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.ErrorInputLabel
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent10
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseOutlinedTextInput(
    text: String,
    onValueChanged: (String) -> Unit,
    label: String? = null,
    error: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    enabled: Boolean = true,
    hasSmallClickable: Boolean = false,
    smallClickableText: String = "",
    smallClickableClick: () -> Unit = { },
    readOnly: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        BasicTextField(
            value = text,
            enabled = enabled,
            readOnly = readOnly,
            minLines = minLines,
            textStyle = OutlinedButtonLabel,
            visualTransformation = visualTransformation,
            onValueChange = {
                //text = it
                onValueChanged(it)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = (if (enabled) Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                .border(
                    brush = BaseGradientBrush,
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                ) else Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                .border(
                    color = WhiteSemiTransparent25,
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                )),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = text,
                    innerTextField = innerTextField,
                    visualTransformation = visualTransformation,
                    enabled = enabled,
                    singleLine = singleLine,
                    interactionSource = interactionSource,
                    trailingIcon = trailingIcon,
                    label = if (label != null) {
                        {
                            Text(label)
                        }
                    } else null,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = WhiteSemiTransparent10,
                        focusedContainerColor = WhiteSemiTransparent10,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                    ),
                )
            },
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!TextUtils.isEmpty(error) && isError) {
                ShowErrorText(error)
            }
            if (!TextUtils.isEmpty(smallClickableText) && hasSmallClickable) {
                SmallClickableText(
                    smallClickableText,
                    onClick = smallClickableClick,
                    modifier = Modifier.padding(horizontal = 50.dp),
                )
            }
        }
    }
}

@Composable
fun ShowErrorText(text: String) {
    Text(
        text = text,
        style = ErrorInputLabel,
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp)
    )
}

@Preview
@Composable
fun BaseOutlinedTextInputPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var text by remember {
                mutableStateOf("")
            }
            BaseOutlinedTextInput(
                text = text,
                onValueChanged = { text = it },
                label = "Connecteam",
                error = "error",
                isError = true,
                hasSmallClickable = true,
                smallClickableText = "Click",
            )
        }
    }
}