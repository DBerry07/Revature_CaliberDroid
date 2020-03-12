package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Category

class SkillArrayAdapter(context: Context, val layout: Int, val categories: ArrayList<Category> )
    : ArrayAdapter<Category>(context, layout, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(layout, parent, false)
        val category = getItem(position)
        (view as TextView).text = category!!.skillCategory

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

}