package com.spacex.spacexlaunchers.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormatter(date: String): String {
    val odt = OffsetDateTime.parse(date)
    val dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu", Locale.US)
    return dtf.format(odt)
}