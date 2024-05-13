package com.benki.taskmanager.utils

import java.util.Calendar

object DateTimeUtils {
    fun convertLongToDate(date: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
        }
        return "%02d/%02d/%d".format(
            calendar.get(Calendar.DATE),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
    }

    fun convertTimeToMillis(hour: Int, minute: Int): Long {
        return ((hour * 60 * 60 * 1000) + (minute * 60 * 1000)).toLong()
    }

    fun convertMillisToTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val formattedHours = hours.toString().padStart(2, '0')
        val formattedMinutes = minutes.toString().padStart(2, '0')
        return "$formattedHours:$formattedMinutes"
    }

    fun convertMillsToMinutes(mills: Long): Int {
        return (mills / 1000 / 60).toInt()
    }

    fun convertLongToHHMMAMPM(date: Long): String {
        Calendar.getInstance().apply {
            timeInMillis = date

            val hour = get(Calendar.HOUR_OF_DAY)
            val minute = get(Calendar.MINUTE)
            val amPm = if (hour < 12) "AM" else "PM"

            val formattedHour = if (hour == 0 || hour == 12) 12 else hour % 12

            return "%02d:%02d $amPm".format(formattedHour, minute)
        }

    }
}