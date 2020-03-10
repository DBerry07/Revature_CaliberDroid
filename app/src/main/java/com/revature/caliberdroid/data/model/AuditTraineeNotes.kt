package com.revature.caliberdroid.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class AuditTraineeNotes(val noteId: Long, val weekNumber: Int, val batch: Batch, val traineeId: Long) : BaseObservable(), SortedListAdapter.ViewModel{

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

    constructor(noteId: Long, weekNumber: Int, content: String, technicalStatus: String, batch: Batch, traineeId: Long) : this(noteId, weekNumber, batch, traineeId) {
        this.content = content
        this.technicalStatus = technicalStatus
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is AuditTraineeNotes) {
            val other = model as AuditTraineeNotes
            return other.noteId == noteId
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is AuditTraineeNotes) {
            val other = model as AuditTraineeNotes
            return technicalStatus == other.technicalStatus && content == other.content
        }
        return false
    }
}