package com.revature.caliberdroid.data.model

import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

class TraineeWithNotes() : SortedListAdapter.ViewModel {

    var trainee = MutableLiveData<Trainee>()
    var auditTraineeNotes = MutableLiveData<AuditTraineeNotes?>()

    constructor(trainee: Trainee) : this() {
        this.trainee.value = trainee
    }

    constructor(trainee: Trainee, auditTraineeNotes: AuditTraineeNotes) : this() {
        this.trainee.value = trainee
        this.auditTraineeNotes.value = auditTraineeNotes
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is TraineeWithNotes) {
            val other = model as TraineeWithNotes
            return trainee.value!!.isSameModelAs(other.trainee.value)
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is TraineeWithNotes) {
            val other = model as TraineeWithNotes
            return trainee.value!!.isContentTheSameAs(other.trainee.value) && other.auditTraineeNotes.value!!.isContentTheSameAs(
                other.auditTraineeNotes.value
            )
        }
        return false
    }
}