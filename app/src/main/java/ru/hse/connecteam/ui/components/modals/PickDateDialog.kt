package ru.hse.connecteam.ui.components.modals

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.connecteam.shared.utils.getDate
import ru.hse.connecteam.ui.theme.ConnecteamTheme

/**
 * The date picker dialog itself.
 *
 * Preferably use onStandardDateSelected and convert it to String in BL-layer instead.
 * `onDateSelected` param is deprecated.
 * @param onStandardDateSelected takes Long
 * @param onDateSelected takes formatted to dd.MM.yyyy Date
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickDateDialog(
    onDateSelected: (String) -> Unit = {},
    onStandardDateSelected: (Long?) -> Unit = {},
    onDismiss: () -> Unit,
    isSelectableDate: (Long) -> Boolean = {
        it >= (System.currentTimeMillis() - 24 * 60 * 60 * 1000)
    }
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return isSelectableDate(utcTimeMillis)
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        getDate(it, "dd.MM.yyyy")
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onStandardDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Отмена")
            }
        },
        colors = DatePickerDefaults.colors()
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Preview
@Composable
fun PickDateDialogPreview() {
    ConnecteamTheme(darkTheme = true) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            var date by remember {
                mutableStateOf("Open date picker dialog")
            }

            var showDatePicker by remember {
                mutableStateOf(false)
            }

            Box(contentAlignment = Alignment.Center) {
                Button(onClick = { showDatePicker = true }) {
                    Text(text = date)
                }
            }

            if (showDatePicker) {
                PickDateDialog(
                    onDateSelected = { date = it },
                    onDismiss = { showDatePicker = false }
                )
            }
        }
    }
}