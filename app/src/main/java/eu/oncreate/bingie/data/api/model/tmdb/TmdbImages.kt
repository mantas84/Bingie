package eu.oncreate.bingie.data.api.model.tmdb

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TmdbImages(
    @Json(name = "backdrops")
    val backdrops: List<Backdrop>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "posters")
    val posters: List<Poster>
) : Parcelable
