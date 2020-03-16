package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable

data class Grade(
    var assessmentId: Long? = 0,
    var traineeId: Long? = 0) : BaseObservable() {

    var gradeId: Long = -1L
        set(value) {
            field = value
            notifyChange()
        }
    var dateReceived: String? = ""
        set(value) {
            field = value
            notifyChange()
        }
    var score: Int? = 0
        set(value) {
            field = value
            notifyChange()
        }

    constructor(gradeId: Long,dateReceived: String, score: Int, assessmentId: Long, traineeId: Long): this(assessmentId,traineeId) {
        this.gradeId = gradeId
        this.dateReceived = dateReceived
        this.score = score
    }

}