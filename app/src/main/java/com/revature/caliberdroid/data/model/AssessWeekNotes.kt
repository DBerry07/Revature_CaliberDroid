package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class AssessWeekNotes(var weekNumber: Int,
                           var batchAverage: Float,
                           var notes: String?,
                           var batch: Batch?,
                           var assessments: MutableLiveData<List<Assessment>>,
                           var grades: MutableLiveData<List<Grade>>,
                           var traineeNotes: MutableLiveData<List<Note>>
) : SortedListAdapter.ViewModel, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readParcelable(Batch::class.java.classLoader),
        TODO("assessments"),
        TODO("grades"),
        TODO("traineeNotes")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(weekNumber)
        parcel.writeFloat(batchAverage)
        parcel.writeString(notes)
        parcel.writeParcelable(batch, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AssessWeekNotes> {
        override fun createFromParcel(parcel: Parcel): AssessWeekNotes {
            return AssessWeekNotes(parcel)
        }

        override fun newArray(size: Int): Array<AssessWeekNotes?> {
            return arrayOfNulls(size)
        }
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}