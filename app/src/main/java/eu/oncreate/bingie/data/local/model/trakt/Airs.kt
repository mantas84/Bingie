package eu.oncreate.bingie.data.local.model.trakt

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Airs(
    @ColumnInfo(name = "parent_id")
    val parentId: Int,
    @ColumnInfo(name = "day")
    val day: String?,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "timezone")
    val timezone: String
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.Airs, parentId: Int): Airs {
            return Airs(parentId, item.day, item.time, item.timezone)
        }

        fun toBe(airs: Airs): eu.oncreate.bingie.data.api.model.Airs {
            return eu.oncreate.bingie.data.api.model.Airs(airs.day, airs.time, airs.timezone)
        }
    }
}
