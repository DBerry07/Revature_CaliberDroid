package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.LocationsAdapter
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationInterface
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationStatusInterface
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository
import com.revature.caliberdroid.databinding.FragmentSettingsLocationsBinding


class LocationsFragment : Fragment(){
    private var _binding : FragmentSettingsLocationsBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

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

                    rvLocations.adapter = LocationsAdapter(locations, EditLocationListener(), EditLocationStatusListener())
                    for (location in locations) {
                        Log.d("Locations", "Location: ${location.toString()}")
                    }
                } else {
                    Log.d("Locations", "locationsViewModel is null")
                }
            })

            btnAddLocation.setOnClickListener{
                navController?.navigate(R.id.action_locationsFragment_to_addLocationFragment)
            }
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
}
