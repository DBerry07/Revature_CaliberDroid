package com.revature.caliberdroid.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.revature.caliberdroid.data.api.APIHandler.context
import timber.log.Timber
import java.util.*


object KeyboardUtil {
    fun hideSoftKeyboard(context: Context, view: View) {
        Timber.d("closing keyboard")

        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)

    }
}