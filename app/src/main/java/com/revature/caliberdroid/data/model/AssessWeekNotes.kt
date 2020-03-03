package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

class AssessWeekNotes() : SortedListAdapter.ViewModel, Parcelable {

    var weekNumber: String? = ""
    var batchAverage: Float = 0f
    var notes: String? = ""

    constructor(weekNumber: String, batchAverage: Float, notes: String) : this() {
        this.weekNumber = weekNumber
        this.batchAverage = batchAverage
        this.notes = notes
    }

    constructor(parcel: Parcel) : this() {
        weekNumber = parcel.readString()
        batchAverage = parcel.readFloat()
        notes = parcel.readString()
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(weekNumber)
        parcel.writeFloat(batchAverage)
        parcel.writeString(notes)
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
}