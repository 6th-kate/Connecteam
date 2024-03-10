package ru.hse.connecteam.features.gamelist.presentation.screens.filters.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.connecteam.shared.utils.getDate
import ru.hse.connecteam.ui.components.images.GradientIcon
import ru.hse.connecteam.ui.components.inputs.BaseOutlinedTextInput
import ru.hse.connecteam.ui.components.modals.PickDateDialog
import ru.hse.connecteam.ui.theme.ConnecteamTheme
import ru.hse.connecteam.ui.theme.SpanButtonWhiteLabel

@Composable
fun RangeDatePicker(
    fromDate: String = "",
    endDate: String = "",
    fromDateChanged: (Long?) -> Unit,
    endDateChanged: (Long?) -> Unit,
    isFromDateSelectable: (Long) -> Boolean,
    isEndDateSelectable: (Long) -> Boolean,
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            var datePickerFromExpanded by remember { mutableStateOf(false) }
            Text("от -", style = SpanButtonWhiteLabel, modifier = Modifier.padding(15.dp))
            Spacer(modifier = Modifier.width(35.dp))
            BaseOutlinedTextInput(
                text = fromDate,
                onValueChanged = {},
                readOnly = true,
                placeholder = "ХХ.ХХ.ХХХХ",
                trailingIcon = {
                    IconButton(onClick = { datePickerFromExpanded = true }) {
                        GradientIcon(image = Icons.Rounded.CalendarMonth)
                    }
                }
            )
            if (datePickerFromExpanded) {
                PickDateDialog(
                    onStandardDateSelected = { fromDateChanged(it) },
                    onDismiss = { datePickerFromExpanded = false },
                    isSelectableDate = { isFromDateSelectable(it) }
                )
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            var datePickerEndExpanded by remember { mutableStateOf(false) }
            Text("до -", style = SpanButtonWhiteLabel, modifier = Modifier.padding(15.dp))
            Spacer(modifier = Modifier.width(35.dp))
            BaseOutlinedTextInput(
                text = endDate,
                onValueChanged = {},
                readOnly = true,
                placeholder = "ХХ.ХХ.ХХХХ",
                trailingIcon = {
                    IconButton(onClick = { datePickerEndExpanded = true }) {
                        GradientIcon(image = Icons.Rounded.CalendarMonth)
                    }
                }
            )
            if (datePickerEndExpanded) {
                PickDateDialog(
                    onStandardDateSelected = { endDateChanged(it) },
                    onDismiss = { datePickerEndExpanded = false },
                    isSelectableDate = { isEndDateSelectable(it) }
                )
            }
        }
    }
}

@Preview
@Composable
fun RangeDatePickerPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var fromDate by remember { mutableStateOf("") }
            var endDate by remember { mutableStateOf("") }
            var fromDateLong: Long? by remember { mutableStateOf(null) }
            var endDateLong: Long? by remember { mutableStateOf(null) }
            RangeDatePicker(
                fromDate,
                endDate,
                fromDateChanged = {
                    fromDate = if (it == null) "" else getDate(it, "dd.MM.yyyy")
                    fromDateLong = it
                },
                endDateChanged = {
                    endDate = if (it == null) "" else getDate(it, "dd.MM.yyyy")
                    endDateLong = it
                },
                isFromDateSelectable = {
                    if (endDateLong != null) {
                        (it <= (System.currentTimeMillis())) &&
                                (it < endDateLong!!)
                    } else {
                        it <= (System.currentTimeMillis())
                    }
                },
                isEndDateSelectable = {
                    if (fromDateLong != null) {
                        (it <= (System.currentTimeMillis())) &&
                                (it > fromDateLong!!)
                    } else {
                        it <= (System.currentTimeMillis())
                    }
                }
            )
        }
    }
}