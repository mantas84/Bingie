package eu.oncreate.bingie.fragment.details

import android.os.Parcelable
import eu.oncreate.bingie.fragment.list.ShowWithImages
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InitialDetailsState(val item: ShowWithImages) : Parcelable
