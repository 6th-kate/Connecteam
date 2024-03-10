package ru.hse.connecteam.features.gamelist.presentation.screens.filters.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.shared.models.game.SimplePlayer
import ru.hse.connecteam.shared.utils.paging.PaginationState
import ru.hse.connecteam.ui.components.animated.LoadingAnimation
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.theme.BaseGradientBrush
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.DefaultBlack
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel
import ru.hse.connecteam.ui.theme.WhiteSemiTransparent25

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersDropDown(
    items: List<SimplePlayer>,
    selectedItem: String,
    onItemSelected: (SimplePlayer) -> Unit,
    paginationState: PaginationState,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(true) }
    val lazyColumnListState = rememberLazyListState()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.then(
            Modifier.wrapContentWidth()
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.menuAnchor()) {
                BaseOutlinedTextInput(
                    text = selectedItem,
                    onValueChanged = {},
                    label = "Имя игрока",
                    //readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { }) {
                            GradientIcon(
                                image = if (expanded) Icons.Outlined.KeyboardArrowUp
                                else Icons.Outlined.KeyboardArrowDown
                            )
                        }
                    },
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .border(
                            shape = RoundedCornerShape(16.dp),
                            brush = BaseGradientBrush,
                            width = 1.dp
                        )
                        .background(color = DefaultBlack)
                        .exposedDropdownSize(true)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 300.dp)
                            .padding(horizontal = 15.dp)
                    ) {
                        LazyColumn(
                            state = lazyColumnListState,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(items = items) { item ->
                                Text(
                                    modifier = Modifier
                                        .padding(
                                            vertical = 15.dp,
                                        )
                                        .clickable {
                                            expanded = false
                                            onItemSelected(item)
                                            Log.i("info", "inside dropdown $selectedItem")
                                        },
                                    text = "${item.name} ${item.surname}",
                                    style = SpanButtonWhiteLabel,
                                )
                                HorizontalDivider(
                                    color = WhiteSemiTransparent25,
                                )
                            }
                            item(
                                key = paginationState,
                            ) {
                                if (paginationState == PaginationState.LOADING ||
                                    paginationState == PaginationState.PAGINATING
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(15.dp)
                                    ) {
                                        LoadingAnimation(indicatorSize = 20.dp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PlayerDropDownPreview() {
    ConnecteamTheme(darkTheme = true) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            val items = mutableStateListOf(
                SimplePlayer("player", "playerson1"),
                SimplePlayer("player", "playerson2"),
                SimplePlayer("player", "playerson3"),
                SimplePlayer("player", "playerson4"),
                SimplePlayer("player", "playerson5"),
                SimplePlayer("player", "playerson6")
            )
            var selectedItem by remember {
                mutableStateOf("${items[2].name} ${items[2].surname}")
            }
            Column {
                PlayersDropDown(
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = {
                        selectedItem = "${it.name} ${it.surname}"
                        items.add(SimplePlayer("player", "playerson7"))
                        Log.i("info", "changed selectedItem to $selectedItem")
                    },
                    paginationState = PaginationState.LOADING
                )
                BaseOutlinedTextInput(text = "", onValueChanged = {})
            }
        }
    }
}