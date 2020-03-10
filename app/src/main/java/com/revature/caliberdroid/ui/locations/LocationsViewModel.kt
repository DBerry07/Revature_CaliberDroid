package com.revature.caliberdroid.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository

class LocationsViewModel : ViewModel() {

    lateinit var locationsLiveData: LiveData< ArrayList<Location> >
    val selectedLocationLiveData = MutableLiveData<Location>()

    fun getLocations(){
        locationsLiveData = LocationRepository.getLocations()
    }

    companion object{
        fun addLocation(location:Location){
            LocationRepository.addLocation(location)
            LocationsViewModel().getLocations()
        }

        fun editLocation(location:Location){
            LocationRepository.editLocation(location)
            LocationsViewModel().getLocations()
        }
    }
}