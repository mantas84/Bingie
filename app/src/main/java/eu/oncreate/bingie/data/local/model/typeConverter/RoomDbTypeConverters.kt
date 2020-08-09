package eu.oncreate.bingie.data.local.model.typeConverter

import androidx.room.TypeConverter

class Converters() {
    @TypeConverter
    fun listToString(value: List<String>?) = value?.joinToString(separator = ",, ").orEmpty()

    @TypeConverter
    fun stringToList(value: String?) = value?.split(delimiters = *arrayOf(",, ")).orEmpty()
}
