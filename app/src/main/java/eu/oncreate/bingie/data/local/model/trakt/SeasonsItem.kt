package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.local.model.typeConverter.IdsConverter

@Entity
@TypeConverters(IdsConverter::class)
data class SeasonsItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "aired_episodes")
    val airedEpisodes: Int,
    @ColumnInfo(name = "episode_count")
    val episodeCount: Int,
    @ColumnInfo(name = "first_aired")
    val firstAired: String?,
    @ColumnInfo(name = "ids")
    val ids: Ids,
    @ColumnInfo(name = "network")
    val network: String?,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "overview")
    val overview: String?,
    @ColumnInfo(name = "rating")
    val rating: Double,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "votes")
    val votes: Int,
    @ColumnInfo(name = "seriesTraktId")
    val seriesTraktId: Int
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.trakt.SeasonsItem, seriesTraktId: Int): SeasonsItem {
            return SeasonsItem(
                airedEpisodes = item.airedEpisodes,
                episodeCount = item.episodeCount,
                firstAired = item.firstAired,
                ids = Ids.toLocal(item.ids),
                network = item.network,
                number = item.number,
                overview = item.overview,
                rating = item.rating,
                title = item.title,
                votes = item.votes,
                seriesTraktId = seriesTraktId
            )
        }

        fun toBe(seasonsItem: SeasonsItem): eu.oncreate.bingie.data.api.model.trakt.SeasonsItem {
            return eu.oncreate.bingie.data.api.model.trakt.SeasonsItem(
                airedEpisodes = seasonsItem.airedEpisodes,
                episodeCount = seasonsItem.episodeCount,
                firstAired = seasonsItem.firstAired,
                ids = Ids.toBe(seasonsItem.ids),
                network = seasonsItem.network,
                number = seasonsItem.number,
                overview = seasonsItem.overview,
                rating = seasonsItem.rating,
                title = seasonsItem.title,
                votes = seasonsItem.votes
            )
        }
    }
}
