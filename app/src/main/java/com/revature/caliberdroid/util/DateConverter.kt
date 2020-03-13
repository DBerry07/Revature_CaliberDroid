package com.revature.caliberdroid.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.sql.Timestamp;

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
        val d = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy"))
        // *1000 to make it milliseconds
        return d.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000
    }

    // REQUIRES API 26
    @RequiresApi(Build.VERSION_CODES.O)
    fun postDateString(inputDate: String): String {
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(inputDate, inputFormatter)

        return outputFormatter.format(date) + "T05:00:00.000Z"
    }


}