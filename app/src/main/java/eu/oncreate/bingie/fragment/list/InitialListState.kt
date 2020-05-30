package eu.oncreate.bingie.fragment.list

import android.os.Parcelable
import com.airbnb.mvrx.PersistState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InitialListState(@PersistState val searchQuery: String = "") : Parcelable
