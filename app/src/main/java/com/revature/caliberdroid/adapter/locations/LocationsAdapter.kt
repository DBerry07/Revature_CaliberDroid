package com.revature.caliberdroid.adapter.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationInterface
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationStatusInterface
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.databinding.ItemSettingsLocationBinding
import timber.log.Timber

class LocationsAdapter(val editListener:EditLocationInterface, val editLocationStatusListener: EditLocationStatusInterface): RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>(){
    val sortedList = SortedList<Location>(Location::class.java, SortedLocationListCallback(this))


//Need to implement data binding in recycler view adapter
    class LocationsViewHolder(binding: ItemSettingsLocationBinding) : RecyclerView.ViewHolder(binding.root){
        var mBinding:ItemSettingsLocationBinding

        init{
            mBinding = binding
        }

        fun bind(item: Location){
            mBinding.model = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ItemSettingsLocationBinding = ItemSettingsLocationBinding.inflate(layoutInflater,parent,false)
        return LocationsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return sortedList.size()
    }

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        val location: Location = sortedList.get(position)
        val imageActiveStatus = holder.mBinding.imgActiveStatus
        setActiveStatusImage(location, imageActiveStatus)
        holder.mBinding.imgEdit.setOnClickListener{
            editListener.onEditLocation(location)
        }
        imageActiveStatus.setOnClickListener{
            editLocationStatusListener.editLocationStatus(location,imageActiveStatus)
        }
        val model: Location = sortedList.get(position)
        holder.bind(model)
    }

    private fun setActiveStatusImage(location:Location, imgView: ImageView){
        if(location.active){
            imgView.setImageResource(R.drawable.ic_active_green)
            imgView.setBackgroundResource(R.drawable.background_active_status)
        }else{
            imgView.setImageResource(R.drawable.ic_inactive_red)
            imgView.setBackgroundResource(R.drawable.background_inactive_status)
        }
    }

    fun add(model: Location){
        sortedList.add(model)
    }

    fun remove(model: Location){
        sortedList.remove(model)
    }

    fun add(models: ArrayList<Location>){
        sortedList.addAll(models)
    }

    fun remove(models: ArrayList<Location>){
        sortedList.beginBatchedUpdates()
        for(i in 0 until models.size){
            sortedList.remove(models.get(i))
        }
        sortedList.endBatchedUpdates()
    }

    fun replaceAll(_models: ArrayList<Location>){
        Timber.d("Updated, filtered locations: $_models")
        sortedList.beginBatchedUpdates()
        var count:Int = sortedList.size() - 1
        while(count >= 0){
            val currentLocation: Location = sortedList.get(count)
            if( !_models.contains(currentLocation) ) {
                sortedList.remove(currentLocation)
            }
            count--
        }
        sortedList.addAll(_models)
        sortedList.endBatchedUpdates()
    }
}