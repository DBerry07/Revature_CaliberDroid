package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class AuditWeekNotes(val notesID: Long, val weekNumber: Int, val overallStatus: String?, val overallNotes: String?) : SortedListAdapter.ViewModel, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(notesID)
        parcel.writeInt(weekNumber)
        parcel.writeString(overallStatus)
        parcel.writeString(overallNotes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuditWeekNotes> {
        override fun createFromParcel(parcel: Parcel): AuditWeekNotes {
            return AuditWeekNotes(parcel)
        }

        override fun newArray(size: Int): Array<AuditWeekNotes?> {
            return arrayOfNulls(size)
        }
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("Not yet implemented")
    }

}

