package com.revature.caliberdroid.ui.trainees

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.ItemTraineeSwitchBinding

class SwitchTraineeAdapter(val batches: ArrayList<Batch>, val onClickBatchToSwitch: SwitchTraineeFragment.SwitchTraineeOnClickInterface): RecyclerView.Adapter<SwitchTraineeAdapter.SwitchTraineeViewHolder>(){
    private var _binding: ItemTraineeSwitchBinding?  = null
    private val binding get() = _binding!!

    inner class SwitchTraineeViewHolder( val binding: ItemTraineeSwitchBinding ): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwitchTraineeViewHolder {
        _binding = ItemTraineeSwitchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SwitchTraineeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return batches.size
    }

    override fun onBindViewHolder(holder: SwitchTraineeViewHolder, position: Int) {
        val currentBatch = batches.get(position)
        holder.binding.batch = currentBatch
        holder.binding.root.setOnClickListener{
            onClickBatchToSwitch.onSwitchTrainee(currentBatch)
        }
    }
}