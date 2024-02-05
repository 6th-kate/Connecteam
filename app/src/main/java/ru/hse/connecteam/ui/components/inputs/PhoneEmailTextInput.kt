package ru.hse.connecteam.ui.components.inputs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@Composable
fun PhoneEmailTextInput(
    text: String,
    onValueChanged: (String) -> Unit,
    error: String = "",
    isError: Boolean = false,
) {
    BaseOutlinedTextInput(
        text = text,
        onValueChanged = onValueChanged,
        error = error,
        isError = isError,
        label = "Email",
        visualTransformation = PhoneEmailVisualTransformation(),
    )
}

class PhoneEmailVisualTransformation : VisualTransformation {
    private val maxLength = 35

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text
        if (trimmed.isEmpty() || trimmed.contains(Regex("[^0-9]"))) return TransformedText(
            AnnotatedString(trimmed.toString()),
            OffsetMapping.Identity
        )

        return PhoneVisualTransformation("+7 (000) 000-00-00", '0').filter(text)
    }
}

class PhoneVisualTransformation(val mask: String, val maskChar: Char) : VisualTransformation {
    private val maxLength = mask.count { it == maskChar }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskChar) {
                    val nextDigitIndex = mask.indexOf(maskChar, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskChar))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        return maskChar == other.maskChar
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}

@Preview
@Composable
fun PhoneEmailTextInputPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var text by remember {
                mutableStateOf("")
            }
            PhoneEmailTextInput(text, { text = it })
        }
    }
}
