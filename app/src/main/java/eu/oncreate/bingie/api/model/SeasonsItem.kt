package eu.oncreate.bingie.api.model

import com.squareup.moshi.Json

data class SeasonsItem(
    @Json(name = "aired_episodes")
    val airedEpisodes: Int,
    @Json(name = "episode_count")
    val episodeCount: Int,
    @Json(name = "first_aired")
    val firstAired: String,
    @Json(name = "ids")
    val ids: Ids,
    @Json(name = "network")
    val network: String,
    @Json(name = "number")
    val number: Int,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "rating")
    val rating: Double,
    @Json(name = "title")
    val title: String,
    @Json(name = "votes")
    val votes: Int
)
