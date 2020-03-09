package com.revature.caliberdroid.data.model

import androidx.databinding.BaseObservable
import com.revature.caliberdroid.BR

data class Note(var weekNumber: Int? = -1,
                var batchId: Long? = -1
                    ) : BaseObservable() {
    var noteId: Long = -1
    var noteContent: String? = ""
    var noteType: String? = ""
    var traineeId: Long? = -1

    constructor(noteId: Long, noteContent: String, noteType: String, weekNumber: Int, batchId: Long, traineeId: Long) : this(weekNumber,batchId) {
        this.noteId = noteId
        this.noteContent = noteContent
        this.noteType = noteType
        this.traineeId = traineeId
    }
}