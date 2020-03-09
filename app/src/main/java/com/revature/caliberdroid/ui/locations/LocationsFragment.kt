package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.LocationsAdapter
import com.revature.caliberdroid.adapter.locations.listeners.EditLocationInterface
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.databinding.FragmentSettingsLocationsBinding
import timber.log.Timber


class LocationsFragment : Fragment(){
    private var _binding : FragmentSettingsLocationsBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private var navController: NavController? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ) : View?{
        navController = findNavController()
        locationsViewModel.getLocations()
        _binding = FragmentSettingsLocationsBinding.inflate(layoutInflater)

        binding.apply {
            lifecycleOwner = this@LocationsFragment
            locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, Observer { locations->
                if(locations != null){

                    rvLocations.adapter = LocationsAdapter(locations, EditLocationListener())
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
        }

        return binding.root
    }

    inner class EditLocationListener: EditLocationInterface {
        override fun onEditLocation(location: Location){
            locationsViewModel.selectedLocationLiveData.value = location
            navController?.navigate(R.id.action_locationsFragment_to_editLocationFragment)
        }
    }
}
