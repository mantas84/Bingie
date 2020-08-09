package eu.oncreate.bingie.data.api.model.fanart

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seasonbanner(
    @Json(name = "id")
    val id: String,
    @Json(name = "lang")
    val lang: String,
    @Json(name = "likes")
    val likes: String,
    @Json(name = "season")
    val season: String,
    @Json(name = "url")
    val url: String
) : Parcelable
