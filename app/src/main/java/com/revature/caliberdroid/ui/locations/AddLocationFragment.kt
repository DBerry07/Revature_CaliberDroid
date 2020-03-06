package com.revature.caliberdroid.ui.locations

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.view.get
import com.revature.caliberdroid.adapter.SettingsSpinnerItemAdapter
import com.revature.caliberdroid.adapter.locations.LocationSpinnerAdapter
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository

import com.revature.caliberdroid.databinding.FragmentSettingsAddLocationBinding
import com.revature.caliberdroid.util.FieldValidator
import timber.log.Timber

class AddLocationFragment : Fragment() {
    private var _binding: FragmentSettingsAddLocationBinding? = null
    private val binding get() = _binding!!
    private var selectedState: String = ""
    private val validationString = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsAddLocationBinding.inflate(layoutInflater)
        val context = context!!
        binding.apply {
            var states = arrayOfNulls<String>(FieldValidator.StatesList.size)
            for (i in 0 until FieldValidator.StatesList.size) {
                states.set(
                    i,
                    FieldValidator.TwoLetterStatesList.get(i) + " " + FieldValidator.StatesList.get(
                        i
                    )
                )
            }
            val locationsSpinnerAdapter = LocationSpinnerAdapter(
                context,
                states
            )
//            val spinner: Spinner = inLocationFields.spnState
//            spinner.adapter = locationsSpinnerAdapter
//            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                    Timber.d("Item selected: ${spinner.getItemAtPosition(position)}")
//                    selectedState = spinner.getItemAtPosition(position).toString()
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                }
//            }
            btnAddLocation.setOnClickListener {
                Timber.d("Attempting to add location.")
                if (
                    LocationsFieldValidator.validateFields(
                        validationString,
                        inLocationFields.etCompanyName,
                        inLocationFields.etCity,
                        inLocationFields.etZipCode,
                        inLocationFields.etStreetAddress,
                        inLocationFields.etState
                    )
                ) {
                    Timber.d("Entry passed validation.")
                    var locationToCreate = Location(
                        inLocationFields.etCompanyName.text.toString(),
                        inLocationFields.etCity.text.toString(),
                        inLocationFields.etZipCode.text.toString(),
                        inLocationFields.etStreetAddress.text.toString(),
                        inLocationFields.etState.text.toString(),
                        true
                    )
                    Timber.d("New location to add: ${locationToCreate.toString()}")
                    LocationRepository.addLocation(locationToCreate)
                } else {
                    Timber.d("Validation of fields failed: "+validationString.toString())
                }
            }
        }

        return binding.root
    }
}
