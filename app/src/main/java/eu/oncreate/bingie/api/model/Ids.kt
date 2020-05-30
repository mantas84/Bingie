package eu.oncreate.bingie.api.model

import com.squareup.moshi.Json

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
)
