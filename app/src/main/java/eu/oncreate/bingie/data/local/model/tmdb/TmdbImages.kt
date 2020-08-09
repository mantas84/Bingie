package eu.oncreate.bingie.data.local.model.tmdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.local.model.typeConverter.BackdropConverter
import eu.oncreate.bingie.data.local.model.typeConverter.PosterConverter

@Entity
@TypeConverters(BackdropConverter::class, PosterConverter::class)
data class TmdbImages(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "backdrops")
    val backdrops: List<Backdrop>,
    @ColumnInfo(name = "posters")
    val posters: List<Poster>
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.tmdb.TmdbImages): TmdbImages {
            return TmdbImages(
                id = item.id,
                backdrops = item.backdrops.map { Backdrop.toLocal(it) },
                posters = item.posters.map { Poster.toLocal(it, item.id) })
        }

        fun toBe(tmdbImages: TmdbImages): eu.oncreate.bingie.data.api.model.tmdb.TmdbImages {
            return eu.oncreate.bingie.data.api.model.tmdb.TmdbImages(
                id = tmdbImages.id,
                backdrops = tmdbImages.backdrops.map { Backdrop.toBe(it) },
                posters = tmdbImages.posters.map { Poster.toBe(it) }
            )
        }

        val EMPTY = TmdbImages(
            backdrops = emptyList(),
            id = -1,
            posters = emptyList()
        )
    }
}
