package eu.oncreate.bingie.data.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ids(
    @Json(name = "imdb")
    val imdb: String?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "tmdb")
    val tmdb: Int?,
    @Json(name = "trakt")
    val trakt: Int,
    @Json(name = "tvdb")
    val tvdb: Int?,
    @Json(name = "tvrage")
    val tvrage: String?
) : Parcelable
