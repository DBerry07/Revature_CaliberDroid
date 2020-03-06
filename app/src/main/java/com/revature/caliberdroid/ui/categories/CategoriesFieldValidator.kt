package com.revature.caliberdroid.ui.categories

import com.revature.caliberdroid.util.FieldValidator
import java.lang.StringBuilder

object CategoriesFieldValidator {

    fun validateFields( validationString: StringBuilder, categoryName: String):Boolean{
        if( FieldValidator.isEmptyString(categoryName) ){
            validationString.clear().append("Category name cannot be empty.")
            return false
        }

        if( !FieldValidator.isValidPlainText(categoryName) ){
            validationString.clear().append("Category name cannot have the following characters: "+ FieldValidator.excludedSpecialCharacters)
            return false
        }

        return true
    }
}