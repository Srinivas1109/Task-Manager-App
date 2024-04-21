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

    fun convertMillsToMinutes(mills: Long): Int {
        return (mills / 1000 / 60).toInt()
    }
}