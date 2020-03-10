package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class Trainee(
    val traineeId: Long,
    var resourceId: String? = "",
    var name: String? = "",
    var email: String? = "",
    var trainingStatus: String? = "",
    var batchId: Long? = 0,
    var phoneNumber: String? = "",
    var skypeId: String? = "",
    var profileUrl: String? = "",
    var recruiterName: String? = "",
    var college: String? = "",
    var degree: String? = "",
    var major: String? = "",
    var techScreenerName: String? = "",
    //Had to alter techScreenScore from Long to Any to allow null from API
    var techScreenScore: Any? = 0,
    var projectCompletion: String? = "",
    var flagStatus: String? = "",
    @Bindable
    var _flagNotes: String? = "",
    var flagAuthor: String? = "",
    var flagTimestamp: String? = ""
) : BaseObservable(), SortedListAdapter.ViewModel, Parcelable {
    var flagNotes: String
    @Bindable get() = _flagNotes!!
        set(value) {
            _flagNotes = value
            notifyChange()
        }
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(traineeId)
        parcel.writeString(resourceId)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(trainingStatus)
        parcel.writeValue(batchId)
        parcel.writeString(phoneNumber)
        parcel.writeString(skypeId)
        parcel.writeString(profileUrl)
        parcel.writeString(recruiterName)
        parcel.writeString(college)
        parcel.writeString(degree)
        parcel.writeString(major)
        parcel.writeString(techScreenerName)
        parcel.writeValue(techScreenScore)
        parcel.writeString(projectCompletion)
        parcel.writeString(flagStatus)
        parcel.writeString(flagNotes)
        parcel.writeString(flagAuthor)
        parcel.writeString(flagTimestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Trainee> {
        override fun createFromParcel(parcel: Parcel): Trainee {
            return Trainee(parcel)
        }

        override fun newArray(size: Int): Array<Trainee?> {
            return arrayOfNulls(size)
        }
    }

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is Trainee) {
            val other = model as Trainee
            return traineeId == other.traineeId
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is Trainee) {
            val other = model as Trainee
            return this == other
        }
        return false
    }
}