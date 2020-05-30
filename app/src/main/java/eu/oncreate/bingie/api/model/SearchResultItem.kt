package eu.oncreate.bingie.api.model

import com.squareup.moshi.Json

data class SearchResultItem(
    @Json(name = "score")
    val score: Double,
    @Json(name = "show")
    val show: Show,
    @Json(name = "type")
    val type: String
)
