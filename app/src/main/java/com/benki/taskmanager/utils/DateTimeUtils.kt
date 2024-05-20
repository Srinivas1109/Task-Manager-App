package com.benki.taskmanager.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

    fun convertMillisToDateTime(millis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun convertTimeToMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        return calendar.timeInMillis
    }

    fun convertMillisToTime(millis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(calendar.time)
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

    fun convertHHMMToLong(hour: Int, minutes: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)
        return calendar.timeInMillis
    }

    fun convertHHMMToTime(hour: Int, minutes: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minutes)

        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}