package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository

import com.revature.caliberdroid.databinding.FragmentSettingsAddLocationBinding
import timber.log.Timber

class AddLocationFragment : Fragment() {
    private var _binding: FragmentSettingsAddLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsAddLocationBinding.inflate(layoutInflater)

        binding.apply {

            btnAddLocation.setOnClickListener {
                Timber.d("Attempting to add location.")
                if(LocationsFieldValidator.validateFields(
                        inLocationFields.etCompanyName.text.toString()
                    )
                ){
//                var locationToCreate = Location(
//                    inLocationFields.etCompanyName.text.toString(),
//                    inLocationFields.etCity.text.toString(),
//                    inLocationFields.etZipCode.text.toString(),
//                    inLocationFields.etStreetAddress.text.toString(),
//                    inLocationFields.etState.text.toString(),
//                    true
//                )
//                Timber.d("New location to add: ${locationToCreate.toString()}")
//                LocationRepository.addLocation(locationToCreate)
                }
            }
        }

        return binding.root
    }

}
