package eu.oncreate.bingie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.local.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.typeConverter.CharacterartConverter
import eu.oncreate.bingie.data.local.model.typeConverter.ClearartConverter
import eu.oncreate.bingie.data.local.model.typeConverter.ClearlogoConverter
import eu.oncreate.bingie.data.local.model.typeConverter.HdclearartConverter
import eu.oncreate.bingie.data.local.model.typeConverter.HdtvlogoConverter
import eu.oncreate.bingie.data.local.model.typeConverter.SeasonbannerConverter
import eu.oncreate.bingie.data.local.model.typeConverter.SeasonposterConverter
import eu.oncreate.bingie.data.local.model.typeConverter.SeasonthumbConverter
import eu.oncreate.bingie.data.local.model.typeConverter.ShowbackgroundConverter
import eu.oncreate.bingie.data.local.model.typeConverter.TvbannerConverter
import eu.oncreate.bingie.data.local.model.typeConverter.TvposterConverter
import eu.oncreate.bingie.data.local.model.typeConverter.TvthumbConverter

@Dao
@TypeConverters(
    CharacterartConverter::class,
    ClearartConverter::class,
    ClearlogoConverter::class,
    HdclearartConverter::class,
    HdtvlogoConverter::class,
    SeasonbannerConverter::class,
    SeasonposterConverter::class,
    SeasonthumbConverter::class,
    ShowbackgroundConverter::class,
    TvbannerConverter::class,
    TvposterConverter::class,
    TvthumbConverter::class
)
interface FanartImageDao {

    @Query("SELECT * FROM fanartimages WHERE thetvdbId = :id")
    suspend fun getFanart(id: Int): List<FanartImages>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFanart(fanartImages: FanartImages)

    @Query("DELETE FROM fanartimages")
    fun deleteAllFanarts()
}
