package com.revature.caliberdroid.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat.getDateInstance
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateConverter {
    fun getDate(timestamp: Long): String {
//        return DateTimeFormatter.ISO_DATE.format(Instant.ofEpochMilli(timestamp))

        val sdf = getDateInstance()
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }

    // REQUIRES API 26
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimestamp(date: String): Long {
        val d = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM DD, uuuu"))
        return d.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
    }

}