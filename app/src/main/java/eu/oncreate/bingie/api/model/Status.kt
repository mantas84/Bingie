package eu.oncreate.bingie.api.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Status : Parcelable {

    @Json(name = "returning series")
    ReturningSeries,

    @Json(name = "in production")
    InProduction,

    @Json(name = "planned")
    Planned,

    @Json(name = "canceled")
    Canceled,

    @Json(name = "ended")
    Ended,
}
