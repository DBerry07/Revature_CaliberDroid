package com.revature.caliberdroid.ui.qualityaudit.weekselection

import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import timber.log.Timber

class QualityAuditWeekSelectionViewModel : ViewModel() {

    lateinit var batchSelected: Batch

    val auditWeekNotesLiveData = ListLiveData<AuditWeekNotes>()

    fun getAuditWeekNotes() {
        if (auditWeekNotesLiveData.size == 0) {
            for (i in 1..batchSelected.weeks) {
                auditWeekNotesLiveData.addItem(AuditWeekNotes(i, overallStatus = null, overallNotes = null))
            }
        }
        BatchRepository.getAuditWeekNotes(batchSelected, auditWeekNotesLiveData)
    }

}
