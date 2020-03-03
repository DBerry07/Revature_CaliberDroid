package com.revature.caliberdroid.adapter.trainers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Trainer

class TrainersAdapter(val trainers: ArrayList<Trainer>): RecyclerView.Adapter<TrainersAdapter.TrainersViewHolder>(){

    class TrainersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTrainerName: TextView = itemView.findViewById<TextView>(R.id.tvTrainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainersViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_trainer,parent,false)
        return TrainersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainers.size
    }

    override fun onBindViewHolder(holder: TrainersViewHolder, position: Int) {
        holder.tvTrainerName.text = trainers.get(position).toString()
    }
}