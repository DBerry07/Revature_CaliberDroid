package com.revature.caliberdroid.ui.categories

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.revature.caliberdroid.R
import java.lang.ClassCastException

class CategoriesDialog(val listener: CategoriesDialogListener, val layoutResource: Int): DialogFragment(){

    interface CategoriesDialogListener{
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        try{

        }catch (e: ClassCastException){
            Log.d("CategoriesDialog","The context must implement CategoriesDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog{
        super.onCreateDialog(savedInstanceState)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            builder.setView(inflater.inflate(layoutResource,null))
                .setPositiveButton(R.string.btn_add,
                    DialogInterface.OnClickListener { dialog, id ->
                    listener.onDialogPositiveClick(this)
                    }
                )
                .setNegativeButton(R.string.btn_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogNegativeClick(this)
                    }
                )
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }


}