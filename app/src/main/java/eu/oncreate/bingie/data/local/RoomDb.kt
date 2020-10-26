package eu.oncreate.bingie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.local.dao.ConfigurationDao
import eu.oncreate.bingie.data.local.dao.FanartImageDao
import eu.oncreate.bingie.data.local.dao.PopularShowItemDao
import eu.oncreate.bingie.data.local.dao.SearchResultItemDao
import eu.oncreate.bingie.data.local.dao.SeasonsItemDao
import eu.oncreate.bingie.data.local.dao.TmdbImagesDao
import eu.oncreate.bingie.data.local.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.tmdb.Configuration
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.model.trakt.PopularShow
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import eu.oncreate.bingie.data.local.model.typeConverter.Converters

@Database(
    entities = [
        FanartImages::class,
        Configuration::class,
        TmdbImages::class,
        SearchResultItem::class,
        PopularShow::class,
        SeasonsItem::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {
    abstract fun configurationDao(): ConfigurationDao
    abstract fun fanartImageDao(): FanartImageDao
    abstract fun searchResultItemDao(): SearchResultItemDao
    abstract fun popularShowsDao(): PopularShowItemDao
    abstract fun seasonsItemDao(): SeasonsItemDao
    abstract fun tmdbImagesDao(): TmdbImagesDao

    companion object {

        private const val DATABASE_NAME = "bingie.db"

        // For Singleton instantiation
        @Volatile
        private var instance: RoomDb? = null

        fun getInstance(context: Context): RoomDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): RoomDb {
            return Room
                .databaseBuilder(context, RoomDb::class.java, DATABASE_NAME)
                .build()
        }
    }
}
