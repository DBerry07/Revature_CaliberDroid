package com.revature.caliberdroid.ui.qualityaudit.weekselection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.data.repository.QualityAuditRepository

class QualityAuditWeekSelectionViewModel : ViewModel() {

    lateinit var batchSelected: Batch

    lateinit var auditWeekNotesLiveData: MutableLiveData<ArrayList<WeekLiveData>>
    fun getAuditWeekNotes() {
        auditWeekNotesLiveData = MutableLiveData()
        lateinit var data: WeekLiveData
        val auditweekNotesList = ArrayList<WeekLiveData>()
            for (i in 1..batchSelected.weeks) {
                data = WeekLiveData()
                data.value = AuditWeekNotes(i)
                auditweekNotesList.add(data)
            }

        auditWeekNotesLiveData.value = auditweekNotesList
        QualityAuditRepository.getAuditWeekNotes(batchSelected, auditWeekNotesLiveData)
    }

    fun addWeek() {
        BatchRepository.addWeek(batchSelected, auditWeekNotesLiveData)
    }

}
