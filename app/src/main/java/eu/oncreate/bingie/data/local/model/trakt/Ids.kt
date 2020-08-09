package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Ids(
    @ColumnInfo(name = "imdb")
    val imdb: String?,
    @ColumnInfo(name = "slug")
    val slug: String?,
    @ColumnInfo(name = "tmdb")
    val tmdb: Int?,
    @ColumnInfo(name = "trakt")
    val trakt: Int,
    @ColumnInfo(name = "tvdb")
    val tvdb: Int?,
    @ColumnInfo(name = "tvrage")
    val tvrage: String?
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.Ids): Ids {
            return Ids(item.imdb, item.slug, item.tmdb, item.trakt, item.tvdb, item.tvrage)
        }

        fun toBe(ids: Ids): eu.oncreate.bingie.data.api.model.Ids {
            return eu.oncreate.bingie.data.api.model.Ids(
                ids.imdb,
                ids.slug,
                ids.tmdb,
                ids.trakt,
                ids.tvdb,
                ids.tvrage
            )
        }
    }
}
