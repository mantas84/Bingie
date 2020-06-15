package eu.oncreate.bingie.api.model.tmdb

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Poster(
    @Json(name = "aspect_ratio")
    val aspectRatio: Double,
    @Json(name = "file_path")
    val filePath: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "iso_639_1")
    val iso6391: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "width")
    val width: Int
) : Parcelable
