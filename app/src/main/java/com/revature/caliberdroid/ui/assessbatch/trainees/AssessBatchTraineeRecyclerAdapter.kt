package com.revature.caliberdroid.ui.assessbatch.trainees

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Trainee

class AssessBatchTraineeRecyclerAdapter : RecyclerView.Adapter<ViewHolder>(){

    private var items : List<Trainee> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return TraineeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_assess_batch_trainee,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class TraineeViewHolder constructor(
        itemView: View
    ): ViewHolder(itemView){

        fun bind(){

        }
    }



}