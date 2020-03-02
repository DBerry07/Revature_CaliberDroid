package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable

data class Batch(
    val batchID: Long,
    var trainingName: String? = "",
    var trainingType: String? = "",
    var skillType: String? = "",
    var trainerName: String? = "",
    var coTrainerName: String? = "",
    var locationID: Long = 0,
    var location: String? = "",
    var startDate: Long = 0,
    var endDate: Long = 0,
    var goodGrade: Int = 0,
    var passingGrade: Int = 0,
    var weeks: Int = 0): Parcelable {
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

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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