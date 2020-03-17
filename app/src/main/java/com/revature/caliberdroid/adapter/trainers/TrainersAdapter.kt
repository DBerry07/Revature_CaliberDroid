package com.revature.caliberdroid.adapter.trainers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.trainers.listeners.EditTrainerInterface
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.databinding.ItemSettingsTrainerBinding
import timber.log.Timber

class TrainersAdapter(val editListener: EditTrainerInterface): RecyclerView.Adapter<TrainersAdapter.TrainersViewHolder>(){
    val sortedList = SortedList<Trainer>(Trainer::class.java, SortedTrainerListCallback(this))

    class TrainersViewHolder(binding: ItemSettingsTrainerBinding) : RecyclerView.ViewHolder(binding.root){
        val mBinding: ItemSettingsTrainerBinding = binding

        fun bind(item: Trainer){
            mBinding.model = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ItemSettingsTrainerBinding = ItemSettingsTrainerBinding.inflate(layoutInflater, parent, false)
        return TrainersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    override fun onBindViewHolder(holder: TrainersViewHolder, position: Int) {
        val trainer: Trainer = sortedList.get(position)
        holder.mBinding.imgEdit.setOnClickListener {
            editListener.onEditTrainer(trainer)
        }
        holder.mBinding.left.setOnClickListener {
            expandCard(holder.mBinding.expandableRows, holder.mBinding.imgExpandIcon)
        }
        holder.bind( sortedList.get(position) )
    }

    fun add(model: Trainer){
        sortedList.add(model)
    }

    fun remove(model: Trainer){
        sortedList.remove(model)
    }

    fun add(models: ArrayList<Trainer>){
        sortedList.addAll(models)
    }

    fun remove(models: ArrayList<Trainer>){
        sortedList.beginBatchedUpdates()
        for(i in 0 until models.size){
            sortedList.remove(models.get(i))
        }
        sortedList.endBatchedUpdates()
    }

    fun replaceAll(_models: ArrayList<Trainer>){
        sortedList.beginBatchedUpdates()
        var count:Int = sortedList.size() - 1
        while(count >= 0){
            val current: Trainer = sortedList.get(count)
            if( !_models.contains(current) ) {
                sortedList.remove(current)
            }
            count--
        }
        sortedList.addAll(_models)
        sortedList.endBatchedUpdates()
    }



    private fun expandCard(expandableView:LinearLayout, imgExpandIcon:ImageView) {
        if( expandableView.isVisible ){
            Timber.i("Is Visible")
            expandableView.visibility = View.GONE
            imgExpandIcon.setImageResource(com.revature.caliberdroid.R.drawable.ic_expand_arrow)
        } else {
            Timber.i("Not Visible")
            expandableView.visibility = View.VISIBLE
            imgExpandIcon.setImageResource(com.revature.caliberdroid.R.drawable.ic_collapse_arrow)
        }
    }
}