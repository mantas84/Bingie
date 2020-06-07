package eu.oncreate.bingie.api.model.tmdb

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Configuration(
    @Json(name = "change_keys")
    val changeKeys: List<String>,
    @Json(name = "images")
    val images: Images
) : Parcelable
