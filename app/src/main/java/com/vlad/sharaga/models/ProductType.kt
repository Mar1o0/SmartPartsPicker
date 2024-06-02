package com.vlad.sharaga.models

import com.vlad.sharaga.R

enum class ProductType(
    val id: String,
    val nameResId: Int,
    val iconResId: Int
) {
    CPU(
        id = "CPU",
        nameResId = R.string.cpu,
        iconResId = R.drawable.cpu
    ),
    MOTHERBOARD(
        id = "MOTHERBOARD",
        nameResId = R.string.motherboard,
        iconResId = R.drawable.motherboard
    ),
    RAM(
        id = "RAM",
        nameResId = R.string.ram,
        iconResId = R.drawable.ram
    ),
    STORAGE(
        id = "STORAGE",
        nameResId = R.string.storage,
        iconResId = R.drawable.storage
    ),
    GPU(
        id = "GPU",
        nameResId = R.string.gpu,
        iconResId = R.drawable.gpu
    ),
    POWER_SUPPLY(
        id = "POWER_SUPPLY",
        nameResId = R.string.power_supply,
        iconResId = R.drawable.power_supply
    ),
    CASING(
        id = "CASING",
        nameResId = R.string.casing,
        iconResId = R.drawable.casing
    );

    companion object {
        fun fromId(id: String): ProductType {
            return entries.first { it.id == id }
        }
    }
}