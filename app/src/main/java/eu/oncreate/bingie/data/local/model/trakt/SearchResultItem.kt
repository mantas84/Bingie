package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchResultItem(
    @ColumnInfo(name = "score")
    val score: Double,
//    @ColumnInfo(name = "show")
    @Embedded
    val show: Show,
    @ColumnInfo(name = "type")
    val type: String,
    @PrimaryKey
    val parentId: Int = show.traktId
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.trakt.SearchResultItem): SearchResultItem {
            return SearchResultItem(
                score = item.score,
                show = Show.toLocal(item.show),
                type = item.type
            )
        }

        fun toBe(searchResultItem: SearchResultItem): eu.oncreate.bingie.data.api.model.trakt.SearchResultItem {
            return eu.oncreate.bingie.data.api.model.trakt.SearchResultItem(
                score = searchResultItem.score,
                show = Show.toBe(searchResultItem.show),
                type = searchResultItem.type
            )
        }
    }
}
