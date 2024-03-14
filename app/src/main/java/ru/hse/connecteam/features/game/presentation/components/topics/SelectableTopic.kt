package ru.hse.connecteam.features.game.presentation.components.topics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.features.game.domain.SelectableTopicViewModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel
import ru.hse.connecteam.ui.components.utils.coloredShadow
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultBlack
import ru.hse.connecteam.ui.theme.DisabledButtonLabel
import ru.hse.connecteam.ui.theme.LightBlueGradient
import ru.hse.connecteam.ui.theme.LightGreenGradient
import ru.hse.connecteam.ui.theme.OutlinedButtonLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@Composable
fun SelectableTopic(
    topic: SelectableTopicViewModel,
    onClick: () -> Unit = {},
) {
    Button(
        modifier = Modifier.wrapContentWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
        contentPadding = PaddingValues(horizontal = 5.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = (if (topic.enabled) Modifier
                .border(
                    brush = BaseGradientBrush,
                    width = 1.dp,
                    shape = RoundedCornerShape(16.dp),
                )
                .then(
                    if (topic.selected) Modifier
                        .coloredShadow(
                            color = LightBlueGradient,
                            offsetX = (-4).dp,
                            offsetY = (-4).dp,
                            borderRadius = 16.dp,
                            blurRadius = 5.dp,
                            spread = 0.3f
                        )
                        .coloredShadow(
                            color = LightGreenGradient,
                            offsetX = 4.dp,
                            offsetY = 4.dp,
                            borderRadius = 16.dp,
                            blurRadius = 5.dp,
                            spread = 0.3f
                        ) else Modifier
                ) else Modifier.border(
                color = WhiteSemiTransparent25,
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
            ))
                .clip(shape = RoundedCornerShape(16.dp))
                .background(color = DefaultBlack)
                .padding(start = 15.dp, end = 15.dp, top = 13.dp, bottom = 13.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = topic.topic.name,
                style = if (topic.enabled) OutlinedButtonLabel else DisabledButtonLabel
            )
        }
    }
}

@Preview
@Composable
fun SelectableTopicPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                SelectableTopic(SelectableTopicViewModel(TopicDomainModel("topic 1")))
                SelectableTopic(
                    SelectableTopicViewModel(
                        TopicDomainModel("topic 1vxfjk;dngjk;fnlj,."),
                        selected = true
                    )
                )
                SelectableTopic(
                    SelectableTopicViewModel(
                        TopicDomainModel("topic 1nfdlk;nmk;"),
                        selected = true
                    )
                )
                SelectableTopic(
                    SelectableTopicViewModel(
                        TopicDomainModel("topic 1"),
                        enabled = false
                    )
                )
                SelectableTopic(
                    SelectableTopicViewModel(
                        TopicDomainModel("topic 1fbdm;klg"),
                        enabled = false,
                        selected = true
                    )
                )
            }
        }
    }
}
