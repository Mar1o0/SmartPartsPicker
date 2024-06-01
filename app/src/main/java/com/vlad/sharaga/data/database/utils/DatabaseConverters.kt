package com.vlad.sharaga.data.database.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

class DatabaseConverters {

    @TypeConverter
    fun List<Int>.toStringData(): String =
        Gson().toJson(this)

    @TypeConverter
    fun String.toListData(): List<Int> =
        Gson().fromJson(this, Array<Int>::class.java).toList()

}