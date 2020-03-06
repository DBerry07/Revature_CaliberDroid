package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.BR

data class AssessWeekNotes(var weekNumber: Int,
                           var batch: Batch?
) : BaseObservable(), SortedListAdapter.ViewModel {

    var batchAverage: Float = 0f
        set(value) {
            field = value
            notifyChange()
        }
    var batchNote: Note = Note(weekNumber,batch!!.batchID)
        set(value) {
            field = value
            notifyChange()
        }
    var assessments: List<Assessment> = arrayListOf()
        set(value) {
            field = value
            notifyChange()
        }
    var grades: List<Grade> = arrayListOf()
        set(value) {
            field = value
            notifyChange()
        }
    var traineeNotes: MutableLiveData<List<Note>> = MutableLiveData(arrayListOf())
        set(value) {
            field = value
            notifyChange()
        }

    constructor(weekNumber: Int, batch: Batch, batchAverage: Float, batchNote: Note, assessments: ArrayList<Assessment>, grades: ArrayList<Grade>) : this(weekNumber,batch) {
        this.batchAverage = batchAverage
        this.batchNote = batchNote
        this.assessments = assessments
        this.grades = grades
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
