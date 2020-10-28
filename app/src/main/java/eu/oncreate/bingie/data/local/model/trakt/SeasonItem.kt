package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.local.model.typeConverter.IdsConverter

@Entity
@TypeConverters(IdsConverter::class)
data class SeasonItem(
    @ColumnInfo(name = "available_translations")
    val availableTranslations: List<String>,
    @ColumnInfo(name = "comment_count")
    val commentCount: Int,
    @ColumnInfo(name = "first_aired")
    val firstAired: String,
    @ColumnInfo(name = "ids")
    val ids: Ids,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "number_abs")
    val numberAbs: String?,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "runtime")
    val runtime: Int,
    @ColumnInfo(name = "season")
    val season: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    @ColumnInfo(name = "votes")
    val votes: Int,
    @ColumnInfo(name = "traktId")
    val traktId: Int
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.trakt.SeasonItem): SeasonItem {
            return SeasonItem(
                availableTranslations = item.availableTranslations,
                commentCount = item.commentCount,
                firstAired = item.firstAired,
                ids = Ids.toLocal(item.ids),
                number = item.number,
                numberAbs = item.numberAbs,
                overview = item.overview,
                rating = item.rating,
                runtime = item.runtime,
                season = item.season,
                title = item.title,
                updatedAt = item.updatedAt,
                votes = item.votes,
                traktId = item.ids.trakt
            )
        }

        fun toBe(seasonItem: SeasonItem): eu.oncreate.bingie.data.api.model.trakt.SeasonItem {
            return eu.oncreate.bingie.data.api.model.trakt.SeasonItem(
                availableTranslations = seasonItem.availableTranslations,
                commentCount = seasonItem.commentCount,
                firstAired = seasonItem.firstAired,
                ids = Ids.toBe(seasonItem.ids),
                number = seasonItem.number,
                numberAbs = seasonItem.numberAbs,
                overview = seasonItem.overview,
                rating = seasonItem.rating,
                runtime = seasonItem.runtime,
                season = seasonItem.season,
                title = seasonItem.title,
                updatedAt = seasonItem.updatedAt,
                votes = seasonItem.votes
            )
        }
    }
}
