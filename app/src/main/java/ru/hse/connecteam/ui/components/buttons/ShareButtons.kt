package ru.hse.connecteam.ui.components.buttons

import android.content.Intent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@Composable
fun ShareButtons(subject: String, copyText: String) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, copyText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
    ) {
        Row(
            modifier = Modifier.weight(weight = 1f, fill = false)
        ) {
            GradientFilledButton(
                text = "Поделиться",
                hasDefaultPadding = false,
                onClick = {
                    startActivity(context, shareIntent, null)
                }
            )
        }
        Row(modifier = Modifier) {
            CopyButton(
                onCopy = { clipboardManager.setText(AnnotatedString(copyText)) }
            )
        }
    }
}

@Composable
fun CopyButton(onCopy: () -> Unit = {}) {
    var copied by mutableStateOf(false)
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), onClick = {
            copied = true
            onCopy()
        }, contentPadding = PaddingValues(all = 0.dp), shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = (if (!copied) Modifier.border(
                brush = BaseGradientBrush,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
            ) else Modifier.border(
                color = WhiteSemiTransparent25,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
            ))
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(
                    horizontal = 10.dp, vertical = 10.dp
                ),
        ) {
            if (!copied) {
                GradientIcon(image = Icons.Outlined.CopyAll, description = "Копировать")
            } else {
                Icon(
                    imageVector = Icons.Outlined.CopyAll,
                    tint = WhiteSemiTransparent25,
                    contentDescription = "Скопировано"
                )
            }
        }
    }
}

@Preview
@Composable
fun ShareButtonPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            ShareButtons(
                "Подключение к игре",
                "Подключитесь к игре по ссылке https://github.com/6th-kate"
            )
        }
    }
}