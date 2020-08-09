package eu.oncreate.bingie.fragment.details

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InitialDetailsState(val traktId: String) : Parcelable
