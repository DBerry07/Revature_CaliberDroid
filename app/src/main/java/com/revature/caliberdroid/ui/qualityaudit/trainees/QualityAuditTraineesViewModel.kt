package com.revature.caliberdroid.ui.qualityaudit.trainees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.TraineeWithNotes
import com.revature.caliberdroid.data.repository.QualityAuditRepository

class QualityAuditTraineesViewModel : ViewModel() {

    val traineesWithNotesLiveData = MutableLiveData<List<TraineeWithNotes>>()

    fun getTraineesWithNotes(batch: Batch, weekNumber: Int) {
        QualityAuditRepository.getTraineesWithNotes(traineesWithNotesLiveData, batch, weekNumber)
    }

}
