package eu.oncreate.bingie.data.local.model.typeConverter

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

abstract class BaseListConverter<E>(val moshi: Moshi = Moshi.Builder().build()) {

    // came from https://gist.github.com/mg6maciej/48f7829e386254bb945b7fc39ce21a19
    protected inline fun <reified E> Moshi.listAdapter(elementType: Type = E::class.java): JsonAdapter<List<E>> {
        return adapter(listType<E>(elementType))
    }

    protected inline fun <reified E> listType(elementType: Type = E::class.java): Type {
        return Types.newParameterizedType(List::class.java, elementType)
    }

    abstract val adapter: JsonAdapter<List<E>>

    @TypeConverter
    fun listToString(value: List<E>?): String = adapter.toJson(value)

    @TypeConverter
    fun stringToList(value: String?) = adapter.fromJson(value)
}