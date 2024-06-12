package com.smart.parts.picker.data.database.tables.assembly

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val ASSEMBLY_TABLE_NAME = "assembly_items"
const val COLUMN_ASSEMBLY_ID = "id"
const val COLUMN_ASSEMBLY_TITLE = "title"
const val COLUMN_ASSEMBLY_PRODUCTS = "products"

@Entity(tableName = ASSEMBLY_TABLE_NAME)
data class AssemblyData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(COLUMN_ASSEMBLY_ID)
    val id: Int = 0,
    @ColumnInfo(COLUMN_ASSEMBLY_TITLE)
    val title: String,
    @ColumnInfo(COLUMN_ASSEMBLY_PRODUCTS)
    val products: List<Int>,
)