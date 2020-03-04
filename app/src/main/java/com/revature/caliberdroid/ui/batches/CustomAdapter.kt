package com.revature.caliberdroid.ui.batches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.ui.batches.BatchesFragment.Companion.onClick
import java.util.*

class CustomAdapter(private val context: Context, private val list: ArrayList<String>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.batch_row, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener (View.OnClickListener {
            Toast.makeText(context,"CUSTOM ITEM SELECTED", Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)

}