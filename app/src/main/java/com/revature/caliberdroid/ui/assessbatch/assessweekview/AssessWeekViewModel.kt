package com.revature.caliberdroid.ui.assessbatch.assessweekview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Week

class AssessWeekViewModel : ViewModel() {
    val weekData: MutableLiveData<Week> by lazy { MutableLiveData<Week>() }

    fun getTrainees(){
        val weekData:Week? = weekData.value

    }

}
