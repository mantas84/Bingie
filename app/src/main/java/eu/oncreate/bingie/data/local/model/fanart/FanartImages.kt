package eu.oncreate.bingie.data.local.model.fanart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import eu.oncreate.bingie.data.api.model.fanart.Characterart
import eu.oncreate.bingie.data.api.model.fanart.Clearart
import eu.oncreate.bingie.data.api.model.fanart.Clearlogo
import eu.oncreate.bingie.data.api.model.fanart.Hdclearart
import eu.oncreate.bingie.data.api.model.fanart.Hdtvlogo
import eu.oncreate.bingie.data.api.model.fanart.Seasonbanner
import eu.oncreate.bingie.data.api.model.fanart.Seasonposter
import eu.oncreate.bingie.data.api.model.fanart.Seasonthumb
import eu.oncreate.bingie.data.api.model.fanart.Showbackground
import eu.oncreate.bingie.data.api.model.fanart.Tvbanner
import eu.oncreate.bingie.data.api.model.fanart.Tvposter
import eu.oncreate.bingie.data.api.model.fanart.Tvthumb
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

@Entity
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
data class FanartImages(
    @PrimaryKey
    val thetvdbId: String,
    @ColumnInfo(name = "characterart")
    val characterart: List<Characterart>?,
    @ColumnInfo(name = "clearart")
    val clearart: List<Clearart>?,
    @ColumnInfo(name = "clearlogo")
    val clearlogo: List<Clearlogo>?,
    @ColumnInfo(name = "hdclearart")
    val hdclearart: List<Hdclearart>?,
    @ColumnInfo(name = "hdtvlogo")
    val hdtvlogo: List<Hdtvlogo>?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "seasonbanner")
    val seasonbanner: List<Seasonbanner>?,
    @ColumnInfo(name = "seasonposter")
    val seasonposter: List<Seasonposter>?,
    @ColumnInfo(name = "seasonthumb")
    val seasonthumb: List<Seasonthumb>?,
    @ColumnInfo(name = "showbackground")
    val showbackground: List<Showbackground>?,
    @ColumnInfo(name = "tvbanner")
    val tvbanner: List<Tvbanner>?,
    @ColumnInfo(name = "tvposter")
    val tvposter: List<Tvposter>?,
    @ColumnInfo(name = "tvthumb")
    val tvthumb: List<Tvthumb>?
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.fanart.FanartImages): FanartImages {
            return FanartImages(
                thetvdbId = item.thetvdbId ?: "0",
                characterart = item.characterart,
                clearart = item.clearart,
                clearlogo = item.clearlogo,
                hdclearart = item.hdclearart,
                hdtvlogo = item.hdtvlogo,
                name = item.name,
                seasonbanner = item.seasonbanner,
                seasonposter = item.seasonposter,
                seasonthumb = item.seasonthumb,
                showbackground = item.showbackground,
                tvbanner = item.tvbanner,
                tvposter = item.tvposter,
                tvthumb = item.tvthumb
            )
        }

        fun toBe(
            fanartImages: FanartImages,
            characterarts: List<Characterart>,
            cleararts: List<Clearart>,
            clearlogos: List<Clearlogo>,
            hdcleararts: List<Hdclearart>,
            hdtvlogos: List<Hdtvlogo>,
            seasonbanners: List<Seasonbanner>,
            seasonposters: List<Seasonposter>,
            seasonthumbs: List<Seasonthumb>,
            showbackgrounds: List<Showbackground>,
            tvbanners: List<Tvbanner>,
            tvposters: List<Tvposter>,
            tvthumbs: List<Tvthumb>
        ): eu.oncreate.bingie.data.api.model.fanart.FanartImages {
            return eu.oncreate.bingie.data.api.model.fanart.FanartImages(
                characterart = characterarts,
                clearart = cleararts,
                clearlogo = clearlogos,
                hdclearart = hdcleararts,
                hdtvlogo = hdtvlogos,
                name = fanartImages.name,
                seasonbanner = seasonbanners,
                seasonposter = seasonposters,
                seasonthumb = seasonthumbs,
                showbackground = showbackgrounds,
                thetvdbId = fanartImages.thetvdbId,
                tvbanner = tvbanners,
                tvposter = tvposters,
                tvthumb = tvthumbs
            )
        }

        val EMPTY = FanartImages(
            "-1",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
    }
}
