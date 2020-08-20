package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem

@Dao
interface SeasonsItemDao {

    @Query("SELECT * FROM seasonsitem WHERE seriesTraktId = :seriesTraktId")
    suspend fun getSeasonsItem(seriesTraktId: Int): List<SeasonsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasonsItem(item: SeasonsItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSeasonsItems(items: List<SeasonsItem>)

    @Query("DELETE FROM seasonsitem")
    fun deleteAllSeasonsItem()
}
