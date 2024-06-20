package com.smart.parts.picker.data.database.tables.assembly

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface AssemblyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyData: AssemblyData): Long

    @Upsert
    suspend fun upsert(assemblyData: AssemblyData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(assemblyData: AssemblyData)

    @Query("SELECT * FROM $ASSEMBLY_TABLE_NAME")
    suspend fun getAll(): List<AssemblyData>

    @Query("SELECT * FROM $ASSEMBLY_TABLE_NAME WHERE $COLUMN_ASSEMBLY_ID = :id")
    suspend fun getById(id: Long): AssemblyData

    @Query("DELETE FROM $ASSEMBLY_TABLE_NAME WHERE $COLUMN_ASSEMBLY_ID = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM $ASSEMBLY_TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $ASSEMBLY_TABLE_NAME WHERE $COLUMN_ASSEMBLY_TITLE LIKE :title")
    suspend fun searchByTitle(title: String): List<AssemblyData>

}