package com.upf464.koonsdiary.presentation.error

import android.os.Parcel
import android.os.Parcelable

data class BiometricViewError(
    val code: Int,
    override val message: String? = null
) : Exception(message), Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(code)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BiometricViewError> {
        override fun createFromParcel(parcel: Parcel): BiometricViewError {
            return BiometricViewError(parcel)
        }

        override fun newArray(size: Int): Array<BiometricViewError?> {
            return arrayOfNulls(size)
        }
    }
}
