package eu.oncreate.bingie.data.local.model.typeConverter

import com.squareup.moshi.JsonAdapter
import eu.oncreate.bingie.data.local.model.tmdb.Backdrop
import eu.oncreate.bingie.data.local.model.tmdb.Poster

class BackdropConverter() : BaseListConverter<Backdrop>() {
    override val adapter: JsonAdapter<List<Backdrop>> = moshi.listAdapter(Backdrop::class.java)
}

class PosterConverter() : BaseListConverter<Poster>() {
    override val adapter: JsonAdapter<List<Poster>> = moshi.listAdapter(Poster::class.java)
}
