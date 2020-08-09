package eu.oncreate.bingie.data.local.model.tmdb

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class Configuration(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "change_keys")
    val changeKeys: List<String>,
    @Embedded
    val images: Images,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.tmdb.Configuration): Configuration {
            return Configuration(
                changeKeys = item.changeKeys,
                images = Images.toLocal(item.images),
                timestamp = Instant.now().epochSecond
            )
        }

        fun toBe(configuration: Configuration): eu.oncreate.bingie.data.api.model.tmdb.Configuration {
            return eu.oncreate.bingie.data.api.model.tmdb.Configuration(
                configuration.changeKeys,
                Images.toBe(configuration.images)
            )
        }
    }
}
