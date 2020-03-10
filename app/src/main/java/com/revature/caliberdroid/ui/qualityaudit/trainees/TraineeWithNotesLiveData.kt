package com.revature.caliberdroid.ui.qualityaudit.trainees

import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditTraineeWithNotes

class TraineeWithNotesLiveData : MutableLiveData<AuditTraineeWithNotes>(),
    SortedListAdapter.ViewModel {
    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is TraineeWithNotesLiveData) {
            val other = model as TraineeWithNotesLiveData
            return value!!.isSameModelAs(other.value)
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is TraineeWithNotesLiveData) {
            val other = model as TraineeWithNotesLiveData
            return value!!.isContentTheSameAs(other.value)
        }
        return false
    }
}