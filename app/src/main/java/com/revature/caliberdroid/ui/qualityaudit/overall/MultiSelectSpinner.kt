package com.revature.caliberdroid.ui.qualityaudit.overall

import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import com.revature.caliberdroid.data.model.Category
import java.util.*


class MultiSelectionCategorySpinner : AppCompatSpinner, OnMultiChoiceClickListener {
    var categories: ArrayList<Category> = arrayListOf()
    var selection: BooleanArray? = null
    var adapter: ArrayAdapter<*>

    constructor(context: Context?) : super(context) {
        adapter = ArrayAdapter<Any?>(context!!, R.layout.simple_spinner_item)
        super.setAdapter(adapter)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        adapter = ArrayAdapter<Any?>(context!!, R.layout.simple_spinner_item)
        super.setAdapter(adapter)
    }

    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
        if (selection !== null && which < selection!!.size) {

            selection!![which] = isChecked;

            adapter.clear();

//            adapter.add(buildSelectedItemString());

        } else {

        }
    }

    override fun performClick(): Boolean {
        val builder = AlertDialog.Builder(context)
        val itemNames = arrayOfNulls<String>(categories.size)

        for (i in categories.indices) {
            itemNames[i] = (categories[i].skillCategory)
        }
        builder.setMultiChoiceItems(itemNames, selection, this)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { arg0, arg1 ->
            // Do nothing
        })
        builder.show()
        return true
    }

    override fun setAdapter(adapter: SpinnerAdapter?) {
        throw RuntimeException("setAdapter is not supported by MultiSelectSpinner.")
    }

    fun setItems(items: ArrayList<Category>) {
        categories = items
        selection = BooleanArray(items.size)
        adapter.clear()
        Arrays.fill(selection, false)
    }

    fun setSelection(selection: ArrayList<Category>) {
        for (i in this.selection!!.indices) {
            this.selection!![i] = false
        }

        for (sel in selection) {
            for (j in 0 until categories.size) {
                if (categories[j].skillCategory.equals(sel.skillCategory)) {
                    this.selection!![j] = true
                }
            }
        }
        adapter.clear()
//        adapter.add(buildSelectedItemString())
    }

    fun getSelectedItems(): ArrayList<Category>? {
        val selectedItems: ArrayList<Category> = ArrayList()
        for (i in 0 until categories.size) {
            if (selection!![i]) {
                selectedItems.add(categories[i])
            }
        }
        return selectedItems
    }

    private fun buildSelectedItemString(): String? {
        val sb = StringBuilder()
        var foundOne = false
        for (i in categories.indices) {
            if (selection!![i]) {
                if (foundOne) {
                    sb.append(", ")
                }
                foundOne = true
                sb.append(categories[i].skillCategory)
            }
        }
        return sb.toString()
    }
}