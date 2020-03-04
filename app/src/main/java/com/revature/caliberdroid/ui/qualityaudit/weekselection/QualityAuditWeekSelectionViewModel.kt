package com.revature.caliberdroid.ui.qualityaudit.weekselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository

class QualityAuditWeekSelectionViewModel : ViewModel() {

    lateinit var batchSelected: Batch

    lateinit var auditWeekNotesLiveData: LiveData<ArrayList<WeekLiveData>>

    fun getAuditWeekNotes() {
        auditWeekNotesLiveData = BatchRepository.getAuditWeekNotes(batchSelected)
    }

}
