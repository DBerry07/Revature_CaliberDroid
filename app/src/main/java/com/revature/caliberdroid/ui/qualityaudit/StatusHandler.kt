package com.revature.caliberdroid.ui.qualityaudit

import android.view.View

interface StatusHandler {
    fun onStatusClick(view: View)

    fun onStatusChoiceClick(view: View)
}