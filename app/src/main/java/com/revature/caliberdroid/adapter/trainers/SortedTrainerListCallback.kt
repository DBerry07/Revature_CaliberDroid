package com.revature.caliberdroid.adapter.trainers

import androidx.recyclerview.widget.SortedList
import com.revature.caliberdroid.data.model.Trainer
import timber.log.Timber

class SortedTrainerListCallback(val trainersAdapter: TrainersAdapter): SortedList.Callback<Trainer>(){
    override fun areItemsTheSame(item1: Trainer?, item2: Trainer?): Boolean {
        return if (item1 != null && item2 != null) {
            item1.trainerID.equals(item2.trainerID)
        }else{
            Timber.d("Trainers are null: $item1 , $item2")
            false
        }
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        trainersAdapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int) {
        trainersAdapter.notifyItemRangeChanged(position, count)
    }

    override fun onInserted(position: Int, count: Int) {
        trainersAdapter.notifyItemRangeInserted(position,count)
    }

    override fun onRemoved(position: Int, count: Int) {
        trainersAdapter.notifyItemRangeRemoved(position, count)
    }

    override fun compare(o1: Trainer?, o2: Trainer?): Int {
        return if(o1 != null && o2 != null){
            o1.name.toLowerCase().compareTo(o2.name.toLowerCase())
        }else{
            Timber.d("Trainers are null: $o1 , $o2")
            0
        }
    }

    override fun areContentsTheSame(oldItem: Trainer?, newItem: Trainer?): Boolean {
        return if (oldItem != null && newItem != null) {
            oldItem.name.equals(newItem.name)
        }else{
            Timber.d("Trainers are null: $oldItem , $newItem")
            false
        }
    }

}