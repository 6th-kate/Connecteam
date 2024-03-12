package ru.hse.connecteam.features.main.presentation.screens.gamelist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultWhite
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun GamesTabRow(
    selectedTabIndex: Int = 0,
    onFiltersOpen: () -> Unit = {},
    moveToTab: (Int) -> Unit = {},
    tabs: List<String> = listOf("Мои", "Участвую")
) {
    Row(
        modifier = Modifier.padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = DefaultWhite,
            divider = {},
            modifier = Modifier.width(250.dp),
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(brush = BaseGradientBrush)
                    )
                }
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, style = SpanButtonWhiteLabel) },
                    selected = selectedTabIndex == index,
                    onClick = { moveToTab(index) },
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onFiltersOpen() }) {
            GradientIcon(image = Icons.Outlined.FilterAlt, description = "Фильтры")
        }
    }
}

@Preview
@Composable
fun GamesTabRowPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column {
                GamesTabRow()
            }
        }
    }
}