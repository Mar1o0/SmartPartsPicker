package com.smart.parts.picker.models.types

enum class ProductType {
    GPU,
    CPU,
    MB,
    RAM,
    STORAGE,
    CHASSIS,
    PSU;

    companion object {
        fun fromString(type: String): ProductType = when (type) {
            "GPU" -> GPU
            "CPU" -> CPU
            "MB" -> MB
            "RAM" -> RAM
            "STORAGE" -> STORAGE
            "CHASSIS" -> CHASSIS
            "PSU" -> PSU
            else -> throw IllegalArgumentException("Unknown product type: $type")
        }
    }
}