package com.revature.caliberdroid.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class AuditTraineeNotes(val weekNumber: Int, val batch: Batch, val traineeId: Long) :
    BaseObservable(), SortedListAdapter.ViewModel {

    var noteId: Long = 0

    @Bindable var content: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @Bindable var technicalStatus: String = "Undefined"
        set(value) {
            field = value
            notifyChange()
        }

    constructor(
        noteId: Long,
        weekNumber: Int,
        content: String,
        technicalStatus: String,
        batch: Batch,
        traineeId: Long
    ) : this(weekNumber, batch, traineeId) {
        this.noteId = noteId
        this.content = content
        this.technicalStatus = technicalStatus
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is AuditTraineeNotes) {
            val other = model as AuditTraineeNotes
            return traineeId == other.traineeId
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is AuditTraineeNotes) {
            val other = model as AuditTraineeNotes
            return (technicalStatus == other.technicalStatus) && (content == other.content)
        }
        return false
    }
}