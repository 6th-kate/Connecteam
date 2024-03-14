package ru.hse.connecteam.features.game.presentation.components.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.connecteam.features.game.domain.SelectableTopicViewModel
import ru.hse.connecteam.features.game.domain.TopicDomainModel
import ru.hse.connecteam.ui.theme.ConnecteamTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicWrap(
    items: List<SelectableTopicViewModel>,
    onTopicTap: (SelectableTopicViewModel) -> Unit = {}
) {
    FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        items.forEach { item ->
            SelectableTopic(topic = item, onClick = { onTopicTap(item) })
        }
    }
}


@Preview
@Composable
fun TopicWrapPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val items = listOf(
                SelectableTopicViewModel(TopicDomainModel("topic 1")),
                SelectableTopicViewModel(
                    TopicDomainModel("topic 1vxfjk;dngjk;fnlj,."),
                    selected = true
                ),
                SelectableTopicViewModel(
                    TopicDomainModel("topic 1nfdlk;nmk;"),
                    selected = true
                ),
                SelectableTopicViewModel(
                    TopicDomainModel("topic 1"),
                    enabled = false
                ),
                SelectableTopicViewModel(
                    TopicDomainModel("topic 1fbdm;klg"),
                    enabled = false,
                    selected = true
                )
            )
            TopicWrap(items)
        }
    }
}