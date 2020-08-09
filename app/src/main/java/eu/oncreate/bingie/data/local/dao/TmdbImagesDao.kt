package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TmdbImagesDao {

    @Query("SELECT * FROM tmdbimages WHERE id = :id")
    fun getTmdbImages(id: Int): Single<List<TmdbImages>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTmdbImages(item: TmdbImages): Completable

    @Query("DELETE FROM tmdbimages")
    fun deleteAllTmdbImages()
}
