package com.revature.caliberdroid.ui.assessbatch.assessweekview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Week
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.repository.AssessWeekRepository

class AssessWeekViewModel(var batchId:Long, var weekNumber:Int) : ViewModel() {

    lateinit var weekData: MutableLiveData<AssessWeekNotes>

    fun initWeekData() {
        weekData = AssessWeekRepository.getAssessWeekData(batchId,weekNumber)
    }

    fun getWeekData():LiveData<AssessWeekNotes> {
        return weekData
    }

}
