package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.databinding.FragmentEditLocationBinding
import kotlinx.android.synthetic.main.include_location_fields.*


class EditLocationFragment : Fragment() {
    private var _binding: FragmentEditLocationBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private var location: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditLocationBinding.inflate(layoutInflater)
        binding.apply {
            locationsViewModel.selectedLocationLiveData.observe(viewLifecycleOwner) { location->

                etCompanyName.setText(location.name)
                etStreetAddress.setText(location.address)
                etCity.setText(location.city)
                etState.setText(location.state)
                etZipCode.setText(location.zipcode)

            }
        }

        return binding.root
    }
}
