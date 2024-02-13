package ru.hse.connecteam.ui.components.inputs

import android.os.Handler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.animated.Shaker
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.OTPInput

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Boolean
) {
    var trigger by remember { mutableLongStateOf(0L) }
    Shaker(trigger = trigger) {
        BasicTextField(
            modifier = modifier,
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    if (onOtpTextChange.invoke(it.text, it.text.length == otpCount)) {
                        trigger = System.currentTimeMillis()
                        Handler().postDelayed({
                            onOtpTextChange.invoke("", false)
                        }, 200)
                    }
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(otpCount) { index ->
                        CharView(
                            index = index,
                            text = otpText
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        )
    }
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(42.dp)
            .border(
                brush = BaseGradientBrush,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 19.dp),
        text = char,
        style = OTPInput,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
fun OTPTextInputPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var text by remember {
                mutableStateOf("")
            }
            OtpTextField(
                otpText = text,
                onOtpTextChange = { i, a ->
                    text = i
                    a && i != "1111"
                },
            )
        }
    }
}
