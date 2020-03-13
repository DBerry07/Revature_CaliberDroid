package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.LocationsAdapter
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationInterface
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationStatusInterface
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository
import com.revature.caliberdroid.databinding.FragmentSettingsLocationsBinding
import timber.log.Timber


class LocationsFragment : Fragment(){
    private var _binding : FragmentSettingsLocationsBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private var navController: NavController? = null
    lateinit var rvAdapter:LocationsAdapter
    var locationsFromAPI = ArrayList<Location>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ) : View?{
        navController = findNavController()
        locationsViewModel.getLocations()
        _binding = FragmentSettingsLocationsBinding.inflate(layoutInflater)
        binding.apply {
            setLifecycleOwner(this@LocationsFragment)
            locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, Observer { locations->
                if(locations != null){
                    locationsFromAPI = locations
                    rvAdapter = LocationsAdapter(EditLocationListener(), EditLocationStatusListener())
                    rvAdapter.sortedList.addAll(locationsFromAPI)
                    rvLocations.adapter = rvAdapter

                    for (location in locations) {
                        Timber.d("Location: $location")
                    }
                } else {
                    Timber.d("locationsViewModel is null")
                }
            })

            btnAddLocation.setOnClickListener{
                navController?.navigate(R.id.action_locationsFragment_to_addLocationFragment)
            }

            searchViewLocations.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {return false}

                override fun onQueryTextChange(newText: String?): Boolean {
                    rvAdapter.replaceAll( filterLocations(locationsFromAPI, newText) )
                    return true
                }

            })
        }

        return binding.root
    }

    inner class EditLocationListener: EditLocationInterface {
        override fun onEditLocation(location: Location){
            locationsViewModel.selectedLocationLiveData.value = location
            navController?.navigate(R.id.action_locationsFragment_to_editLocationFragment)
        }
    }

    inner class EditLocationStatusListener: EditLocationStatusInterface {
        override fun editLocationStatus(location: Location, statusImageView: ImageView) {
            location.active = !location.active
            if(location.active){
                statusImageView.setImageResource(R.drawable.ic_active_green)
                statusImageView.setBackgroundResource(R.drawable.background_active_status)
            }else{
                statusImageView.setImageResource(R.drawable.ic_inactive_red)
                statusImageView.setBackgroundResource(R.drawable.background_inactive_status)
            }
            LocationRepository.editLocation(location)
        }
    }

    fun filterLocations(locations:ArrayList<Location> , _query:String?): ArrayList<Location>{
        val filteredLocations: ArrayList<Location> = ArrayList<Location>()
        if(_query != null){
            val query = _query.toLowerCase()
            for(i in 0 until locations.size){
                val currentLocation = locations.get(i)
                if(currentLocation.name.toLowerCase().contains(query)){
                    filteredLocations.add(currentLocation)
                }
            }
        }
        return filteredLocations
    }
}
