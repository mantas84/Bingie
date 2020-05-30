package eu.oncreate.bingie.api.model

import com.squareup.moshi.Json

data class Show(
    @Json(name = "aired_episodes")
    val airedEpisodes: Int,
    @Json(name = "airs")
    val airs: Airs,
    @Json(name = "available_translations")
    val availableTranslations: List<String>,
    @Json(name = "certification")
    val certification: String?,
    @Json(name = "comment_count")
    val commentCount: Int,
    @Json(name = "country")
    val country: String?,
    @Json(name = "first_aired")
    val firstAired: String?,
    @Json(name = "genres")
    val genres: List<String>,
    @Json(name = "homepage")
    val homepage: String?,
    @Json(name = "ids")
    val ids: Ids,
    @Json(name = "language")
    val language: String?,
    @Json(name = "network")
    val network: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "rating")
    val rating: Double,
    @Json(name = "runtime")
    val runtime: Int,
    @Json(name = "status")
    val status: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "trailer")
    val trailer: String?,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "votes")
    val votes: Int,
    @Json(name = "year")
    val year: Int?
)
