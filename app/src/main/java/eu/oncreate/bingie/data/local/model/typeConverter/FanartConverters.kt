package eu.oncreate.bingie.data.local.model.typeConverter

import com.squareup.moshi.JsonAdapter
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

class CharacterartConverter : BaseListConverter<Characterart>() {
    override val adapter: JsonAdapter<List<Characterart>> =
        moshi.listAdapter(Characterart::class.java)
}

class ClearartConverter : BaseListConverter<Clearart>() {
    override val adapter: JsonAdapter<List<Clearart>> = moshi.listAdapter(Clearart::class.java)
}

class ClearlogoConverter : BaseListConverter<Clearlogo>() {
    override val adapter: JsonAdapter<List<Clearlogo>> =
        moshi.listAdapter(Clearlogo::class.java)
}

class HdclearartConverter : BaseListConverter<Hdclearart>() {
    override val adapter: JsonAdapter<List<Hdclearart>> =
        moshi.listAdapter(Hdclearart::class.java)
}

class HdtvlogoConverter : BaseListConverter<Hdtvlogo>() {
    override val adapter: JsonAdapter<List<Hdtvlogo>> =
        moshi.listAdapter(Hdtvlogo::class.java)
}

class SeasonbannerConverter : BaseListConverter<Seasonbanner>() {
    override val adapter: JsonAdapter<List<Seasonbanner>> =
        moshi.listAdapter(Seasonbanner::class.java)
}

class SeasonposterConverter : BaseListConverter<Seasonposter>() {
    override val adapter: JsonAdapter<List<Seasonposter>> =
        moshi.listAdapter(Seasonposter::class.java)
}

class SeasonthumbConverter : BaseListConverter<Seasonthumb>() {
    override val adapter: JsonAdapter<List<Seasonthumb>> =
        moshi.listAdapter(Seasonthumb::class.java)
}

class ShowbackgroundConverter : BaseListConverter<Showbackground>() {
    override val adapter: JsonAdapter<List<Showbackground>> =
        moshi.listAdapter(Showbackground::class.java)
}

class TvbannerConverter : BaseListConverter<Tvbanner>() {
    override val adapter: JsonAdapter<List<Tvbanner>> =
        moshi.listAdapter(Tvbanner::class.java)
}

class TvposterConverter : BaseListConverter<Tvposter>() {
    override val adapter: JsonAdapter<List<Tvposter>> =
        moshi.listAdapter(Tvposter::class.java)
}

class TvthumbConverter : BaseListConverter<Tvthumb>() {
    override val adapter: JsonAdapter<List<Tvthumb>> =
        moshi.listAdapter(Tvthumb::class.java)
}
