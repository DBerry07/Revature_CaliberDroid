package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.BR
import kotlin.properties.Delegates

data class AuditWeekNotes(val weekNumber: Int) : BaseObservable(), SortedListAdapter.ViewModel, Parcelable {

    @Bindable var overallStatus: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.auditWeekNotes)
        }
    @Bindable var overallNotes: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.auditWeekNotes)
        }

    constructor(parcel: Parcel) : this(
        parcel.readInt()
    ) {
        overallStatus = parcel.readString()
        overallNotes = parcel.readString()
    }

    constructor(weekNumber: Int, overallStatus: String?, overallNotes: String?) : this(weekNumber) {
        this.overallStatus = overallStatus
        this.overallNotes = overallNotes
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        if (model is AuditWeekNotes) {
            val other = model as AuditWeekNotes
            return overallStatus == other.overallStatus && overallNotes == other.overallNotes
        }
        return false
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        if (model is AuditWeekNotes) {
            val other = model as AuditWeekNotes
            return other.weekNumber == weekNumber
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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

}

