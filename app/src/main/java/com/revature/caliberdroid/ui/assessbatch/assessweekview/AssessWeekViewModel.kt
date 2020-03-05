package com.revature.caliberdroid.ui.assessbatch.assessweekview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Week
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.repository.AssessWeekRepository

class AssessWeekViewModel : ViewModel() {

    lateinit var assessWeekNotes: MutableLiveData<AssessWeekNotes>

    fun initWeekData(batchId:Long,weekNumber:Int) {
        assessWeekNotes = AssessWeekRepository.getAssessWeekData(batchId,weekNumber)
    }

    fun getWeekData():LiveData<AssessWeekNotes> {
        return assessWeekNotes
    }

}
