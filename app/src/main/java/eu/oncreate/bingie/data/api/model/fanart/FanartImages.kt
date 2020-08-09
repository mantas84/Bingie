package eu.oncreate.bingie.data.api.model.fanart

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FanartImages(
    @Json(name = "characterart")
    val characterart: List<Characterart>?,
    @Json(name = "clearart")
    val clearart: List<Clearart>?,
    @Json(name = "clearlogo")
    val clearlogo: List<Clearlogo>?,
    @Json(name = "hdclearart")
    val hdclearart: List<Hdclearart>?,
    @Json(name = "hdtvlogo")
    val hdtvlogo: List<Hdtvlogo>?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "seasonbanner")
    val seasonbanner: List<Seasonbanner>?,
    @Json(name = "seasonposter")
    val seasonposter: List<Seasonposter>?,
    @Json(name = "seasonthumb")
    val seasonthumb: List<Seasonthumb>?,
    @Json(name = "showbackground")
    val showbackground: List<Showbackground>?,
    @Json(name = "thetvdb_id")
    val thetvdbId: String?,
    @Json(name = "tvbanner")
    val tvbanner: List<Tvbanner>?,
    @Json(name = "tvposter")
    val tvposter: List<Tvposter>?,
    @Json(name = "tvthumb")
    val tvthumb: List<Tvthumb>?
) : Parcelable
