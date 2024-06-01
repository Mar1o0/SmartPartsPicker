package com.vlad.sharaga.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vlad.sharaga.data.database.tables.assembly.AssemblyDao
import com.vlad.sharaga.data.database.tables.assembly.AssemblyData
import com.vlad.sharaga.data.database.utils.DatabaseConverters

@Database(
    entities = [AssemblyData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assemblyDao(): AssemblyDao

}