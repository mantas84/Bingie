package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SeasonsItemDao {

    @Query("SELECT * FROM seasonsitem WHERE seriesTraktId = :seriesTraktId")
    fun getSeasonsItem(seriesTraktId: Int): Single<List<SeasonsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeasonsItem(item: SeasonsItem): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSeasonsItems(items: List<SeasonsItem>): Completable

    @Query("DELETE FROM seasonsitem")
    fun deleteAllSeasonsItem()
}
