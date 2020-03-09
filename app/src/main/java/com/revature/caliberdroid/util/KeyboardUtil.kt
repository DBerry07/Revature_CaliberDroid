package com.revature.caliberdroid.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

object KeyboardUtil {
    fun hideSoftKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        Objects.requireNonNull(imm)
            .hideSoftInputFromWindow(view.windowToken, 0)
    }
}