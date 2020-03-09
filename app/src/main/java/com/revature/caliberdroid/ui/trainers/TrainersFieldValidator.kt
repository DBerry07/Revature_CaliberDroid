package com.revature.caliberdroid.ui.trainers

import android.widget.EditText
import com.revature.caliberdroid.util.FieldValidator
import java.lang.StringBuilder

object TrainersFieldValidator {

    fun validateFields(
        validationString: StringBuilder,
        etFullName: EditText,
        etEmail: EditText,
        etTitle: EditText
    ): Boolean{

//Full name field validation
        if( FieldValidator.isEmptyString(etFullName.text.toString()) ){
            validationString.clear().append("Full name cannot be empty.")
            return false
        }

        if( !FieldValidator.isValidPlainText(etFullName.text.toString()) ){
            validationString.clear().append("Full name cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

//Email field validation
        if( FieldValidator.isEmptyString(etEmail.text.toString()) ){
            validationString.clear().append("Email cannot be empty.")
            return false
        }

        if( !FieldValidator.isValidEmail(etEmail.text.toString()) ){
            validationString.clear().append("Email entry is not a valid email.")
            return false
        }

//Title field validation
        if( FieldValidator.isEmptyString(etTitle.text.toString()) ){
            validationString.clear().append("Title cannot be empty.")
            return false
        }

        if( !FieldValidator.isValidPlainText(etTitle.text.toString()) ){
            validationString.clear().append("Title cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }
        return true
    }
}