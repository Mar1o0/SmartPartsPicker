package com.vlad.sharaga.data.database.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

class DatabaseConverters {

    @TypeConverter
    fun List<Int>.listToString(): String =
        Gson().toJson(this)

    @TypeConverter
    fun String.stringToList(): List<Int> =
        Gson().fromJson(this, Array<Int>::class.java).toList()

    @TypeConverter
    fun Set<Int>.setToString(): String =
        Gson().toJson(this)

    @TypeConverter
    fun String.stringToSet(): Set<Int> =
        Gson().fromJson(this, Array<Int>::class.java).toSet()

}