package eu.oncreate.bingie.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Airs(
    @Json(name = "day")
    val day: String?,
    @Json(name = "time")
    val time: String?,
    @Json(name = "timezone")
    val timezone: String
) : Parcelable
