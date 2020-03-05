package com.revature.caliberdroid.adapter.trainers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.trainers.listeners.EditTrainerInterface
import com.revature.caliberdroid.data.model.Trainer

class TrainersAdapter(val trainers: ArrayList<Trainer>, val editListener: EditTrainerInterface): RecyclerView.Adapter<TrainersAdapter.TrainersViewHolder>(){

    class TrainersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTrainerName: TextView = itemView.findViewById<TextView>(R.id.tvTrainer)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        val tvTier: TextView = itemView.findViewById(R.id.tvTier)
        val imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainersViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_settings_trainer,parent,false)
        return TrainersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainers.size
    }

    override fun onBindViewHolder(holder: TrainersViewHolder, position: Int) {
        val trainer: Trainer = trainers.get(position)
        holder.tvTrainerName.text = trainer.name
        holder.tvTitle.text = trainer.title
        holder.tvEmail.text = trainer.email
        holder.tvTier.text = trainer.tier
        holder.imgEdit.setOnClickListener {
            editListener.onEditTrainer(trainer)
        }
    }
}