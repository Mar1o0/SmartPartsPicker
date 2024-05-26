package com.vlad.sharaga.ui.category

enum class CategoryType(val id: String) {
    CPU("cpu"),
    MOTHERBOARD("motherboard"),
    RAM("ram"),
    STORAGE("storage"),
    GPU("gpu"),
    POWER_SUPPLY("power_supply"),
    CASING("casing");
    companion object {
        fun fromId(id: String): CategoryType {
            return entries.first { it.id == id }
        }
    }
}