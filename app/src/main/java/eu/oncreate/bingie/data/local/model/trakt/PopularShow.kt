package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PopularShow(
    @ColumnInfo(name = "score")
    val score: Double,
    @Embedded
    val show: Show,
    @ColumnInfo(name = "type")
    val type: String,
    @PrimaryKey
    val parentId: Int = show.traktId
) {
    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.trakt.PopularShow): PopularShow {
            return PopularShow(
                score = item.score,
                show = Show.toLocal(item.show),
                type = item.type
            )
        }

        fun toBe(popularShow: PopularShow): eu.oncreate.bingie.data.api.model.trakt.PopularShow {
            return eu.oncreate.bingie.data.api.model.trakt.PopularShow(
                score = popularShow.score,
                show = Show.toBe(popularShow.show),
                type = popularShow.type
            )
        }

        fun toSearchItem(popularShow: PopularShow): SearchResultItem {
            return SearchResultItem(
                score = popularShow.score,
                show = popularShow.show,
                type = popularShow.type
            )
        }

        fun toShow(searchResultItem: SearchResultItem): PopularShow {
            return PopularShow(
                score = searchResultItem.score,
                show = searchResultItem.show,
                type = searchResultItem.type
            )
        }
    }
}
