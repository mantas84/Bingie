package eu.oncreate.bingie.api.model

import com.squareup.moshi.Json

data class Airs(
    @Json(name = "day")
    val day: Any?,
    @Json(name = "time")
    val time: Any?,
    @Json(name = "timezone")
    val timezone: String
)
