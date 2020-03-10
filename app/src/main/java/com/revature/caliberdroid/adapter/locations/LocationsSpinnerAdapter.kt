package com.revature.caliberdroid.adapter.locations

import android.content.Context
import com.revature.caliberdroid.adapter.SettingsSpinnerItemAdapter
import com.revature.caliberdroid.util.FieldValidator

class LocationSpinnerAdapter(context: Context, listItemsTxt: Array<String?>): SettingsSpinnerItemAdapter(context,listItemsTxt){
    override fun getItem(position: Int): String {
        return FieldValidator.TwoLetterStatesList.get(position)
    }
}