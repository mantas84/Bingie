package eu.oncreate.bingie.data.api.model

import com.squareup.moshi.Json

data class SeasonItem(
    @Json(name = "available_translations")
    val availableTranslations: List<String>,
    @Json(name = "comment_count")
    val commentCount: Int,
    @Json(name = "first_aired")
    val firstAired: String,
    @Json(name = "ids")
    val ids: Ids,
    @Json(name = "number")
    val number: Int,
    @Json(name = "number_abs")
    val numberAbs: String?,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "rating")
    val rating: Double,
    @Json(name = "runtime")
    val runtime: Int,
    @Json(name = "season")
    val season: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "votes")
    val votes: Int
)
