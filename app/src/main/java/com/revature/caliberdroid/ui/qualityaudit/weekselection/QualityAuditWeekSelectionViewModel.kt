package com.revature.caliberdroid.ui.qualityaudit.weekselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import timber.log.Timber

class QualityAuditWeekSelectionViewModel : ViewModel() {

    lateinit var batchSelected: Batch

    lateinit var auditWeekNotesLiveData: MutableLiveData<ArrayList<AuditWeekNotes>>
    fun getAuditWeekNotes() {
        auditWeekNotesLiveData = MutableLiveData()
        val auditweekNotesList = ArrayList<AuditWeekNotes>()
            for (i in 1..batchSelected.weeks) {
                auditweekNotesList.add(AuditWeekNotes(i))
            }

        auditWeekNotesLiveData.value = auditweekNotesList
        BatchRepository.getAuditWeekNotes(batchSelected, auditWeekNotesLiveData)
    }

    fun addWeek() {
        BatchRepository.addWeek(batchSelected, auditWeekNotesLiveData)
    }

}
