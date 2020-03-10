package com.revature.caliberdroid.data.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class TraineeWithNotes(val trainee: Trainee, var auditTraineeNotes: AuditTraineeNotes? = null) : SortedListAdapter.ViewModel {

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is TraineeWithNotes) {
            val other = model as TraineeWithNotes
            return trainee.isSameModelAs(other.trainee) && auditTraineeNotes!!.isSameModelAs(other.auditTraineeNotes)
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is TraineeWithNotes) {
            val other = model as TraineeWithNotes
            return trainee.isContentTheSameAs(other.trainee) == other.auditTraineeNotes!!.isContentTheSameAs(other.auditTraineeNotes)
        }
        return false
    }
}