package com.revature.caliberdroid.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat.getDateInstance
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.util.*

object DateConverter {


    fun getDate(timestamp: Long): String {
//        return DateTimeFormatter.ISO_DATE.format(Instant.ofEpochMilli(timestamp))

        val sdf = getDateInstance()
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    }

}