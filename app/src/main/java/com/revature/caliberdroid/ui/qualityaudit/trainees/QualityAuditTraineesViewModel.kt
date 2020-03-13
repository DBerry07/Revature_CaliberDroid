package com.revature.caliberdroid.ui.qualityaudit.trainees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.QualityAuditRepository

class QualityAuditTraineesViewModel : ViewModel() {

    lateinit var traineesWithNotesLiveData: MutableLiveData<List<TraineeWithNotesLiveData>>

    fun getTraineesWithNotes(batch: Batch, weekNumber: Int) {
        traineesWithNotesLiveData = QualityAuditRepository.getTraineesWithNotes(batch, weekNumber)
    }

}
