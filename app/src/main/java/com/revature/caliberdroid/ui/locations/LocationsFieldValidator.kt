package com.revature.caliberdroid.ui.locations

import android.widget.EditText
import com.revature.caliberdroid.util.FieldValidator
import kotlinx.android.synthetic.main.fragment_settings_add_location.*
import kotlinx.android.synthetic.main.include_location_fields.view.*
import timber.log.Timber
import java.lang.StringBuilder

object LocationsFieldValidator {

    fun validateFields(
        validationString: StringBuilder,
        companyName: EditText,
        city: EditText,
        zipCode: EditText,
        address: EditText
    ): Boolean{
        return validateFields(validationString,companyName, city,zipCode,address,null)
    }

    fun validateFields(
        validationString: StringBuilder,
        companyName: EditText,
        city: EditText,
        zipCode: EditText,
        address: EditText,
        state: EditText?
    ): Boolean{
//company name field validation
        if( FieldValidator.isEmptyString(companyName.text.toString()) ){
            validationString.clear().append("Company name cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(companyName.text.toString()) ){
            validationString.clear().append("Company name cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//address validation
        if( FieldValidator.isEmptyString(address.text.toString()) ){
            validationString.clear().append("Address cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(address.text.toString()) ){
            validationString.clear().append("Address cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//city field validation
        if( FieldValidator.isEmptyString(city.text.toString()) ){
            validationString.clear().append("City cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidPlainText(city.text.toString()) ){
            validationString.clear().append("City cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//zip code validation
        if( FieldValidator.isEmptyString(zipCode.text.toString()) ){
            validationString.clear().append("ZipCode cannot be empty!")
            return false
        }

        if( !FieldValidator.isValidZipCode(zipCode.text.toString()) ){
            validationString.clear().append("Zip Code must be in the form: ##### or #####-####")
            return false
        }

//Only if user is entering (typing) the state
        if(state != null){
//state validation
            if( FieldValidator.isEmptyString(state.text.toString()) ){
                validationString.clear().append("State cannot be empty!")
                return false
            }

            if( !FieldValidator.isValidPlainText(state.text.toString()) ){
                validationString.clear().append("State cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
                return false
            }

        }
        return true
    }
}