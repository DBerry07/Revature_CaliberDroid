package com.revature.caliberdroid.ui.batches

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.ui.batches.BatchesFragment.Companion.onClick
import com.revature.caliberdroid.ui.batches.CustomAdapter.MyViewHolder
import java.util.*


class BatchesAdapter constructor(cont: Context, batchesList: Array<Batch>) {
    private val context: Context = cont
    private val batches: Array<Batch> = batchesList


/*

    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.batch_row, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener { onClick() }
    }

    override fun getItemCount(): Int {
        return batches.size
    }

    class MyViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)
*/



}

