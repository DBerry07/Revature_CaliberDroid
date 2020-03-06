package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Location

object LocationRepository {

    fun getLocations(): LiveData< ArrayList<Location> >{
        val liveData = MutableLiveData< ArrayList<Location> >()
        APIHandler.getLocations(liveData)
        return liveData
    }

    fun addLocation(location:Location){
        APIHandler.addLocation(location)
    }

    fun editLocation(location:Location){
        APIHandler.editLocation(location)
    }
}