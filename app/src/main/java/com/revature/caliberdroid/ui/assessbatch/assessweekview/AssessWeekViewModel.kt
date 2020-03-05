package com.revature.caliberdroid.ui.assessbatch.assessweekview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Week
import com.revature.caliberdroid.data.model.AssessWeekNotes

class AssessWeekViewModel : ViewModel() {

    val weekData: MutableLiveData<Week> by lazy { MutableLiveData<Week>() }
    lateinit var assessWeekNotes: AssessWeekNotes

    fun getTrainees(){
        val weekData:Week? = weekData.value

    }

}
