package com.example.quickpractice.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    const val HOUR_MINUTE_FORMAT = "HH:mm"
    const val DAY_MONTH_YEAR_FORMAT = "dd.MM.yyyy"
    const val TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun getHourDate(input: String) : Pair<String, String> {
        val cleaned = input.substring(0, 19).replace("T", " ")
        val parser = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val date = parser.parse(cleaned)

        date?.let {
            val timeFormat = SimpleDateFormat(HOUR_MINUTE_FORMAT, Locale.getDefault())
            val dateFormat = SimpleDateFormat(DAY_MONTH_YEAR_FORMAT, Locale.getDefault())

            val timeString = timeFormat.format(it)
            val dateString = dateFormat.format(it)

            return Pair(timeString, dateString)
        }
        return Pair("", "")
    }
}