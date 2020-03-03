package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentLocationsBinding


class LocationsFragment : Fragment(){
    private var _binding : FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private val locationModel: LocationsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ) : View?{
        _binding = FragmentLocationsBinding.inflate(layoutInflater)
        binding.apply {
            btnAddLocation.setOnClickListener{
                findNavController().navigate(R.id.action_locationsFragment_to_addLocationFragment)
            }
            btnEditLocation.setOnClickListener{
                findNavController().navigate(R.id.action_locationsFragment_to_editLocationFragment)
            }
        }

        locationModel.getLocations()

        return binding.root
    }
}
