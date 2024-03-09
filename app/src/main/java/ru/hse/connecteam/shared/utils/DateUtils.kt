package ru.hse.connecteam.shared.utils

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.text.SimpleDateFormat
import java.util.Calendar

fun getDate(milliSeconds: Long, dateFormat: String): String {
    val currentLocale = ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0)
    val formatter = SimpleDateFormat(dateFormat, currentLocale)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}
