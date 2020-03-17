package com.revature.caliberdroid.util

object AuditWeekHintConverter {

    fun getHint(notes: String, weekNumber: Int): String {
        if (notes == "") {
            return "Start typing to record your overall QC feedback for week $weekNumber"
        } else {
            return notes
        }
    }
}