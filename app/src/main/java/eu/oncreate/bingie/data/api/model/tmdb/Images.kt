package eu.oncreate.bingie.data.api.model.tmdb

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    @Json(name = "backdrop_sizes")
    val backdropSizes: List<String>,
    @Json(name = "base_url")
    val baseUrl: String,
    @Json(name = "logo_sizes")
    val logoSizes: List<String>,
    @Json(name = "poster_sizes")
    val posterSizes: List<String>,
    @Json(name = "profile_sizes")
    val profileSizes: List<String>,
    @Json(name = "secure_base_url")
    val secureBaseUrl: String,
    @Json(name = "still_sizes")
    val stillSizes: List<String>
) : Parcelable
