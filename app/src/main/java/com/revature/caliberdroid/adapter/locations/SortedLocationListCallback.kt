package com.revature.caliberdroid.adapter.locations

import androidx.recyclerview.widget.SortedList
import com.revature.caliberdroid.data.model.Location
import timber.log.Timber

class SortedLocationListCallback(val locationsAdapter:LocationsAdapter): SortedList.Callback<Location>(){
    override fun areItemsTheSame(item1: Location?, item2: Location?): Boolean {
        return if (item1 != null && item2 != null) {
            item1.locationID.equals(item2.locationID)
        }else{
            Timber.d("Locations are null: $item1 , $item2")
            false
        }
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        locationsAdapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int) {
        locationsAdapter.notifyItemRangeChanged(position, count)
    }

    override fun onInserted(position: Int, count: Int) {
        locationsAdapter.notifyItemRangeInserted(position,count)
    }

    override fun onRemoved(position: Int, count: Int) {
        locationsAdapter.notifyItemRangeRemoved(position, count)
    }

    override fun compare(o1: Location?, o2: Location?): Int {
        return if(o1 != null && o2 != null){
            o1.name.toLowerCase().compareTo(o2.name.toLowerCase())
        }else{
            Timber.d("Locations are null: $o1 , $o2")
            0
        }
    }

    override fun areContentsTheSame(oldItem: Location?, newItem: Location?): Boolean {
        return if (oldItem != null && newItem != null) {
             oldItem.name.equals(newItem.name)
        }else{
            Timber.d("Locations are null: $oldItem , $newItem")
            false
        }
    }


}