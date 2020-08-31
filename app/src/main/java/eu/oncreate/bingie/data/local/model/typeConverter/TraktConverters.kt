package eu.oncreate.bingie.data.local.model.typeConverter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.oncreate.bingie.data.local.model.trakt.Airs
import eu.oncreate.bingie.data.local.model.trakt.Ids
import java.util.Date

class IdsConverter {

    private val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter = moshi.adapter<Ids>(Ids::class.java)

    @TypeConverter
    fun listToString(value: Ids?): String = adapter.toJson(value)

    @TypeConverter
    fun stringToList(value: String?) = adapter.fromJson(value.orEmpty())
}

class AirsConverter {

    private val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).add(
        KotlinJsonAdapterFactory()
    ).build()

    private val adapter = moshi.adapter<Airs>(Airs::class.java)

    @TypeConverter
    fun listToString(value: Airs?): String = adapter.toJson(value)

    @TypeConverter
    fun stringToList(value: String?) = adapter.fromJson(value.orEmpty())
}
