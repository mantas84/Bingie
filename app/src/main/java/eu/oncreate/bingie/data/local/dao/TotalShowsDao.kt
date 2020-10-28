package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.trakt.TotalShows

@Dao
interface TotalShowsDao {

    @Query("SELECT * FROM totalshows order by id")
    suspend fun getAll(): List<TotalShows>

    @Query("SELECT * FROM totalshows WHERE id = :id")
    suspend fun get(id: Int): List<TotalShows>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TotalShows)

    @Query("DELETE FROM totalshows WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM totalshows")
    suspend fun deleteAll()
}
