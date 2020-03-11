package com.revature.caliberdroid.data.model

import androidx.databinding.BaseObservable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

class AuditTraineeWithNotes() : BaseObservable(), SortedListAdapter.ViewModel {

    var trainee: Trainee? = null
        set(value) {
            field = value
            notifyChange()
        }
    var auditTraineeNotes: AuditTraineeNotes? = null
        set(value) {
            field = value
            notifyChange()
        }

    constructor(trainee: Trainee) : this() {
        this.trainee = trainee
    }

    constructor(trainee: Trainee, auditTraineeNotes: AuditTraineeNotes) : this() {
        this.trainee = trainee
        this.auditTraineeNotes = auditTraineeNotes
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is AuditTraineeWithNotes) {
            val other = model as AuditTraineeWithNotes
            return trainee!!.isSameModelAs(other.trainee) && auditTraineeNotes!!.isSameModelAs(
                other.auditTraineeNotes
            )
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is AuditTraineeWithNotes) {
            val other = model as AuditTraineeWithNotes
            return trainee!!.isContentTheSameAs(other.trainee) && other.auditTraineeNotes!!.isContentTheSameAs(
                other.auditTraineeNotes
            )
        }
        return false
    }
}