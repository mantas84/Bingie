package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eu.oncreate.bingie.data.local.model.tmdb.Configuration
import io.reactivex.Single

@Dao
interface ConfigurationDao {

    @Query("SELECT * FROM configuration")
    fun getConfiguration(): Single<List<Configuration>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfiguration(configuration: Configuration)

    @Query("DELETE FROM configuration")
    fun deleteConfiguration()
}
