package com.revature.caliberdroid.ui.locations

import com.revature.caliberdroid.util.FieldValidator
import kotlinx.android.synthetic.main.fragment_settings_add_location.*
import kotlinx.android.synthetic.main.include_location_fields.view.*
import timber.log.Timber

object LocationsFieldValidator {

    fun validateFields(companyName:String): Boolean{
        if( !FieldValidator.isEmptyString(companyName) ){
            Timber.d("Company name cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(companyName) ){
            Timber.d("Company name cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }
        return true
    }
}