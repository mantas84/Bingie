package eu.oncreate.bingie.data.api.model.trakt

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultItem(
    @Json(name = "score")
    val score: Double,
    @Json(name = "show")
    val show: Show,
    @Json(name = "type")
    val type: String
) : Parcelable
