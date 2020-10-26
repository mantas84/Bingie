package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.trakt.PopularShow

@Dao
interface PopularShowItemDao {

    @Query("SELECT * FROM popularshow WHERE traktId = :id")
    suspend fun getPopularShow(id: Int): List<PopularShow>

    @Query("SELECT * FROM popularshow WHERE title LIKE '%' || :query || '%'")
    suspend fun searchPopularShow(query: String): List<PopularShow>

    @Query("SELECT * FROM popularshow")
    suspend fun searchPopularShow(): List<PopularShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularShow(item: PopularShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPopularShow(items: List<PopularShow>)

    @Query("DELETE FROM popularshow  WHERE traktId = :id")
    suspend fun deletePopularShow(id: Int)

    @Query("DELETE FROM popularshow")
    suspend fun deleteAllPopularShows()
}
