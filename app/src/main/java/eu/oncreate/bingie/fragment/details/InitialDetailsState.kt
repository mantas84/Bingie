package eu.oncreate.bingie.fragment.details

import android.os.Parcelable
import eu.oncreate.bingie.api.model.SearchResultItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InitialDetailsState(val item: SearchResultItem) : Parcelable
