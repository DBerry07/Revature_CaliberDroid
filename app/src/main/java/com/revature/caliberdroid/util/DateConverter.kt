package com.revature.caliberdroid.util

import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun getDate(timestamp: Long): String {
//        return DateTimeFormatter.ISO_DATE.format(Instant.ofEpochMilli(timestamp))

        val sdf = getDateInstance()
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }

    fun getTimestamp(date: String): Long {
        val ts = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).parse(date)
        return ts!!.time

        // REQUIRES API 26
        //val d = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM d, yyyy"))
        // *1000 to make it milliseconds
        //return d.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()*1000
    }

    fun postDateString(inputDate: String): String {
        val outputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val inputFormatter = SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH)
        val date = outputFormatter.format(inputFormatter.parse(inputDate)!!)

        return date + "T05:00:00.000Z"
    }


}