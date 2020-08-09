package eu.oncreate.bingie.data.local.model.typeConverter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import eu.oncreate.bingie.data.local.model.trakt.Airs
import eu.oncreate.bingie.data.local.model.trakt.Ids

class IdsConverter() {

    private val moshi = Moshi.Builder().build()

    private val adapter = moshi.adapter<Ids>(Ids::class.java)

    @TypeConverter
    fun listToString(value: Ids?): String = adapter.toJson(value)

    @TypeConverter
    fun stringToList(value: String?) = adapter.fromJson(value.orEmpty())
}

class AirsConverter() {

    private val moshi = Moshi.Builder().build()

    private val adapter = moshi.adapter<Airs>(Airs::class.java)

    @TypeConverter
    fun listToString(value: Airs?): String = adapter.toJson(value)

    @TypeConverter
    fun stringToList(value: String?) = adapter.fromJson(value.orEmpty())
}
