package com.revature.caliberdroid.ui.locations

import android.widget.EditText
import com.revature.caliberdroid.util.FieldValidator
import kotlinx.android.synthetic.main.fragment_settings_add_location.*
import kotlinx.android.synthetic.main.include_location_fields.view.*
import timber.log.Timber

object LocationsFieldValidator {

    fun validateFields(
        companyName: EditText,
        city: EditText,
        zipCode: EditText,
        address: EditText
    ): Boolean{
        return validateFields(companyName, city,zipCode,address,null)
    }

    fun validateFields(
        companyName: EditText,
        city: EditText,
        zipCode: EditText,
        address: EditText,
        state: EditText?
    ): Boolean{
//company name field validation
        if( FieldValidator.isEmptyString(companyName.text.toString()) ){
            Timber.d("Company name cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(companyName.text.toString()) ){
            Timber.d("Company name cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//address validation
        if( FieldValidator.isEmptyString(address.text.toString()) ){
            Timber.d("Address cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(address.text.toString()) ){
            Timber.d("Address cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//city field validation
        if( FieldValidator.isEmptyString(city.text.toString()) ){
            Timber.d("City cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(city.text.toString()) ){
            Timber.d("City cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//zip code validation
        if( FieldValidator.isEmptyString(zipCode.text.toString()) ){
            Timber.d("ZipCode cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidZipCode(zipCode.text.toString()) ){
            Timber.d("Zip Code must be in the form: ##### or #####-####")
            return false
        }

//Only if user is entering (typing) the state
        if(state != null){
//state validation
            if( FieldValidator.isEmptyString(state.text.toString()) ){
                Timber.d("City cannot be empty!")
                return false
            }

            if( !FieldValidator.isValidPlainText(state.text.toString()) ){
                Timber.d("State cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
                return false
            }

        }
        return true
    }
}