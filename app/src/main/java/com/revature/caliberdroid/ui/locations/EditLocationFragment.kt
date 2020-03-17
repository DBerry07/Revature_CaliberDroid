package com.revature.caliberdroid.ui.locations

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.locations.LocationSpinnerAdapter
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.databinding.FragmentSettingsEditLocationBinding
import com.revature.caliberdroid.util.DialogInvalidInput
import com.revature.caliberdroid.util.FieldValidator
import kotlinx.android.synthetic.main.fragment_settings_edit_location.*
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
            selectedState = location.state


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

            val positionOfSelectedState:Int = FieldValidator.TwoLetterStatesList.indexOf(selectedState)
            if(positionOfSelectedState > -1){
                spinner.setSelection(positionOfSelectedState)
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
                    LocationsViewModel.editLocation(location)

                    val imgLoadingIcon:ImageView = binding.imgLoadingIcon
                    val drawable = createLoading(imgLoadingIcon)

                } else {
                    Timber.d("Validation of fields failed: "+validationString.toString())
                    DialogInvalidInput().showInvalidInputDialog(context,view,validationString.toString())
                }
            }


        }
        return binding.root
    }


    private fun createLoading(animationView: ImageView): AnimatedVectorDrawableCompat?{
        val animated = AnimatedVectorDrawableCompat.create(context!!, R.drawable.anim_orange_progress)
        animated?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                animationView.post { animated.start() }
            }

        })
        animationView.setImageDrawable(animated)
        animated?.start()
        return animated
    }
}
