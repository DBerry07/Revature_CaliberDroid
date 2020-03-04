package com.revature.caliberdroid.ui.batches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import java.util.*

class BatchesAdapter(cont: Context, batchesList: LiveData<Batch>) :
    RecyclerView.Adapter<BatchesAdapter.MyViewHolder>() {
    private val context: Context = cont
    private val batches: LiveData<Batch> = batchesList
    private lateinit var skillFocus: String
    private lateinit var location: String
    private lateinit var startDate: Date

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.batch_row, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 1
        //batches.value.
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dateView.text = batches.value?.startDate.toString()
        holder.locationView.text = batches.value?.location
        holder.skillFocusView.text = batches.value?.skillType
        holder.itemView.setOnClickListener( View.OnClickListener {
            Toast.makeText(context,"Batch Adapter Click", Toast.LENGTH_SHORT).show()
        })
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var locationView: TextView = itemView!!.findViewById(R.id.tv_batch_row_location_value)
        var skillFocusView: TextView = itemView!!.findViewById(R.id.tv_batch_row_skill_value)
        var dateView: TextView = itemView!!.findViewById(R.id.tv_batch_row_date_value)
    }

}



