package com.revature.caliberdroid.ui.locations

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.LocationSpinnerAdapter

import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.repository.LocationRepository
import com.revature.caliberdroid.databinding.FragmentSettingsEditLocationBinding
import com.revature.caliberdroid.util.DialogInvalidInput
import com.revature.caliberdroid.util.FieldValidator
import kotlinx.android.synthetic.main.fragment_settings_add_location.*
import kotlinx.android.synthetic.main.include_location_fields.view.*
import kotlinx.android.synthetic.main.item_settings_location.*
import timber.log.Timber


class EditLocationFragment : Fragment() {
    private var _binding: FragmentSettingsEditLocationBinding? = null
    private val binding get() = _binding!!
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private lateinit var location: Location
    private var selectedState: String = ""
    private val validationString = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsEditLocationBinding.inflate(layoutInflater)
        location = locationsViewModel.selectedLocationLiveData.value!!
        val context = context!!
        binding.apply {
            inLocationFields.etCompanyName.setText(location.name)
            inLocationFields.etStreetAddress.setText(location.address)
            inLocationFields.etCity.setText(location.city)
            inLocationFields.etZipCode.setText(location.zipcode)
            //inLocationFields.etState.setText(location.state)


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
            val spinner: Spinner = inLocationFields.spnState
            spinner.adapter = locationsSpinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    Timber.d("Item selected: ${spinner.getItemAtPosition(position)}")
                    selectedState = spinner.getItemAtPosition(position).toString()
                    Timber.d("Selected state: $selectedState")
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }




            btnEditLocation.setOnClickListener {
                if (
                    LocationsFieldValidator.validateFields(
                        validationString,
                        inLocationFields.etCompanyName,
                        inLocationFields.etCity,
                        inLocationFields.etZipCode,
                        inLocationFields.etStreetAddress,
                        null
                    )
                ) {
                    Timber.d("Entry passed validation.")
                    location.name = inLocationFields.etCompanyName.text.toString()
                    location.address = inLocationFields.etStreetAddress.text.toString()
                    location.city = inLocationFields.etCity.text.toString()
                    if( !FieldValidator.isEmptyString(selectedState) ){
                        location.state = selectedState
                    }
                    location.zipcode = inLocationFields.etZipCode.text.toString()

                    Timber.d("Updated location: ${location.toString()}")
                    LocationRepository.editLocation(location)

                    findNavController().navigateUp()
                } else {
                    Timber.d("Validation of fields failed: "+validationString.toString())
                    DialogInvalidInput().showInvalidInputDialog(context,view,validationString.toString())
                }
            }


        }
        return binding.root
    }


}
