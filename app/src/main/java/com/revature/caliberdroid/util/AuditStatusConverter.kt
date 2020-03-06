package com.revature.caliberdroid.util

import com.revature.caliberdroid.R

object AuditStatusConverter {

    fun getImageResourceID(status: String) : Int {
        when (status) {
            "Poor" -> return R.drawable.ic_poor_face
            "Average" -> return R.drawable.ic_average_face
            "Good" -> return R.drawable.ic_good_face
            "Superstar" -> return R.drawable.ic_superstar
            else -> return R.drawable.ic_undefined
        }
    }

}