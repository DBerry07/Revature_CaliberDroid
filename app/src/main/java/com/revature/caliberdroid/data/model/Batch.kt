package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.BR

data class Batch (
    val batchID: Long,
    @Bindable
    var _trainingName: String?,
    var trainingType: String?,
    var skillType: String?,
    var trainerName: String?,
    var coTrainerName: String?,
    var locationID: Long,
    var location: String?,
    var startDate: Long,
    var endDate: Long,
    var goodGrade: Int,
    var passingGrade: Int,
    var weeks: Int) : BaseObservable(), SortedListAdapter.ViewModel, Parcelable {

    var trainingName: String
    @Bindable get() = _trainingName!!
    set(value) {
        _trainingName = value
        notifyPropertyChanged(BR._all)
    }


    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is Batch) {
            val other = model as Batch
            return other.batchID == batchID
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is Batch) {
            val other = model as Batch
            return trainerName == other.trainerName
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(batchID)
        parcel.writeString(_trainingName)
        parcel.writeString(trainingType)
        parcel.writeString(skillType)
        parcel.writeString(trainerName)
        parcel.writeString(coTrainerName)
        parcel.writeLong(locationID)
        parcel.writeString(location)
        parcel.writeLong(startDate)
        parcel.writeLong(endDate)
        parcel.writeInt(goodGrade)
        parcel.writeInt(passingGrade)
        parcel.writeInt(weeks)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Batch> {
        override fun createFromParcel(parcel: Parcel): Batch {
            return Batch(parcel)
        }

        override fun newArray(size: Int): Array<Batch?> {
            return arrayOfNulls(size)
        }
    }

}





