package com.revature.caliberdroid.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.APIHandler.context

class DialogInvalidInput {

    fun showInvalidInputDialog(context: Context?, fragmentView: View?, msg:String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context);
        val dialogView = LayoutInflater.from(context).inflate(
            R.layout.dialog_settings_validation,
            fragmentView!!.findViewById(android.R.id.content)
        )
        dialogView.findViewById<TextView>(R.id.tvWarning).text = msg
        builder.setView(dialogView)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}