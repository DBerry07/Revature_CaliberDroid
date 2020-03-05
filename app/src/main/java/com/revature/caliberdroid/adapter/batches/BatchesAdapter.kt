package com.revature.caliberdroid.adapter.batches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.ui.batches.ManageBatchFragment
import com.revature.caliberdroid.ui.batches.ManageBatchFragmentDirections
import kotlin.collections.ArrayList

class BatchesAdapter(cont: Context, batchesList: ArrayList<Batch>) :
    RecyclerView.Adapter<BatchesAdapter.BatchesViewHolder>() {
    private val context: Context = cont
    private val batches: ArrayList<Batch> = batchesList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BatchesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.batch_row, parent, false)
        return BatchesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return batches.size
    }

    override fun onBindViewHolder(holder: BatchesViewHolder, position: Int) {
        val batch: Batch = batches[position]
        val locationFormatted = formatLocation(batch.location.toString())
        holder.dateView.text = batch.trainerName.toString()
        holder.locationView.text = locationFormatted
        holder.skillFocusView.text = batch.skillType.toString()
        holder.batchName.text = batch._trainingName.toString()

    }

    private fun formatLocation(loc: String): String {
        val strList: List<String> = loc.split(",")
        return strList[0] + ",\n" + loc.substring(strList[0].length+2)
    }

    class BatchesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
        var locationView: TextView = itemView!!.findViewById(R.id.tv_batch_row_location_value)
        var skillFocusView: TextView = itemView!!.findViewById(R.id.tv_batch_row_skill_value)
        var dateView: TextView = itemView!!.findViewById(R.id.tv_batch_row_date_value)
        var batchName: TextView = itemView!!.findViewById(R.id.tv_batch_row_name_value)
        var cardView: ConstraintLayout = itemView!!.findViewById(R.id.constraintLayout_batch_row)

        init {
            cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "onClick item $adapterPosition", Toast.LENGTH_SHORT).show()
        }

    }

}



