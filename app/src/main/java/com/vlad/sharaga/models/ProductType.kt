package com.vlad.sharaga.models

enum class ProductType(val id: String) {
    CPU("CPU"),
    MOTHERBOARD("MOTHERBOARD"),
    RAM("RAM"),
    STORAGE("STORAGE"),
    GPU("GPU"),
    POWER_SUPPLY("POWER_SUPPLY"),
    CASING("CASING");
    companion object {
        fun fromId(id: String): ProductType {
            return entries.first { it.id == id }
        }
    }
}