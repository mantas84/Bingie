package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages

@Dao
interface TmdbImagesDao {

    @Query("SELECT * FROM tmdbimages WHERE id = :id")
    suspend fun getTmdbImages(id: Int): List<TmdbImages>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTmdbImages(item: TmdbImages)

    @Query("DELETE FROM tmdbimages")
    fun deleteAllTmdbImages()
}
