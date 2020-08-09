package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.local.model.typeConverter.AirsConverter
import eu.oncreate.bingie.data.local.model.typeConverter.Converters
import eu.oncreate.bingie.data.local.model.typeConverter.IdsConverter

@Entity
@TypeConverters(IdsConverter::class, AirsConverter::class, Converters::class)
data class Show(
    @ColumnInfo(name = "aired_episodes")
    val airedEpisodes: Int,
    @ColumnInfo(name = "airs")
    val airs: Airs,
    @ColumnInfo(name = "available_translations")
    val availableTranslations: List<String>,
    @ColumnInfo(name = "certification")
    val certification: String?,
    @ColumnInfo(name = "comment_count")
    val commentCount: Int,
    @ColumnInfo(name = "country")
    val country: String?,
    @ColumnInfo(name = "first_aired")
    val firstAired: String?,
    @ColumnInfo(name = "genres")
    val genres: List<String>,
    @ColumnInfo(name = "homepage")
    val homepage: String?,
    @ColumnInfo(name = "ids")
    val ids: Ids,
    @ColumnInfo(name = "language")
    val language: String?,
    @ColumnInfo(name = "network")
    val network: String?,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "runtime")
    val runtime: Int,
    @ColumnInfo(name = "status")
    val status: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "trailer")
    val trailer: String?,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    @ColumnInfo(name = "votes")
    val votes: Int,
    @ColumnInfo(name = "year")
    val year: Int?,
    @ColumnInfo(name = "traktId")
    val traktId: Int
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.Show): Show {
            return Show(
                item.airedEpisodes,
                Airs.toLocal(item.airs, item.ids.trakt),
                item.availableTranslations,
                item.certification,
                item.commentCount,
                item.country,
                item.firstAired,
                item.genres,
                item.homepage,
                Ids.toLocal(item.ids),
                item.language,
                item.network,
                item.overview,
                item.rating,
                item.runtime,
                item.status,
                item.title,
                item.trailer,
                item.updatedAt,
                item.votes,
                item.year,
                item.ids.trakt
            )
        }

        fun toBe(show: Show): eu.oncreate.bingie.data.api.model.Show {
            return eu.oncreate.bingie.data.api.model.Show(
                show.airedEpisodes,
                Airs.toBe(show.airs),
                show.availableTranslations,
                show.certification,
                show.commentCount,
                show.country,
                show.firstAired,
                show.genres,
                show.homepage,
                Ids.toBe(show.ids),
                show.language,
                show.network,
                show.overview,
                show.rating,
                show.runtime,
                show.status,
                show.title,
                show.trailer,
                show.updatedAt,
                show.votes,
                show.year
            )
        }
    }
}
