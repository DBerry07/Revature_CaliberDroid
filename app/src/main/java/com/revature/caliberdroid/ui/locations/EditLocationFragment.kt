package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository
import com.revature.caliberdroid.databinding.FragmentSettingsEditLocationBinding
import com.revature.caliberdroid.ui.locations.LocationsFieldValidator.validateFields
import com.revature.caliberdroid.util.FieldValidator
import kotlinx.android.synthetic.main.fragment_settings_add_location.*
import kotlinx.android.synthetic.main.include_location_fields.view.*
import timber.log.Timber


class EditLocationFragment : Fragment() {
    private var _binding: FragmentSettingsEditLocationBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private lateinit var location: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsEditLocationBinding.inflate(layoutInflater)
        location = locationsViewModel.selectedLocationLiveData.value!!
        binding.apply {
            inLocationFields.etCompanyName.setText(location.name)
            inLocationFields.etStreetAddress.setText(location.address)
            inLocationFields.etCity.setText(location.city)
            inLocationFields.etState.setText(location.state)
            inLocationFields.etZipCode.setText(location.zipcode)

            btnEditLocation.setOnClickListener {
                if( validateFields(
                        inLocationFields.etCompanyName.text.toString()
                    )
                ){

//                    location.name = inLocationFields.etCompanyName.text.toString()
//                    location.address = inLocationFields.etStreetAddress.text.toString()
//                    location.city = inLocationFields.etCity.text.toString()
//                    location.state = inLocationFields.etState.text.toString()
//                    location.zipcode = inLocationFields.etZipCode.text.toString()
//
//                    Timber.d("Updated location: ${location.toString()}")
//                    LocationRepository.editLocation(location)
//
//                    findNavController().navigateUp()
                }
            }
        }
        return binding.root
    }


}
