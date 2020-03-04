package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.LocationsAdapter
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.databinding.FragmentLocationsBinding
import kotlinx.android.synthetic.main.fragment_locations.*


class LocationsFragment : Fragment(){
    private var _binding : FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ) : View?{
        locationsViewModel.getLocations()
        _binding = FragmentLocationsBinding.inflate(layoutInflater)
        binding.apply {
            setLifecycleOwner(this@LocationsFragment)
            locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, Observer { locations->
                if(locations != null){

                    rvLocations.adapter = LocationsAdapter(locations)
                    for (location in locations) {
                        Log.d("Locations", "Location: ${location.toString()}")
                    }
                } else {
                    Log.d("Locations", "locationsViewModel is null")
                }
            })

            btnAddLocation.setOnClickListener{
                findNavController().navigate(R.id.action_locationsFragment_to_addLocationFragment)
            }
        }

        return binding.root
    }
}
