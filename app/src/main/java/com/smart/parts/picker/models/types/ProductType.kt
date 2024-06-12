package com.smart.parts.picker.models.types

enum class ProductType {
    GPU,
    CPU,
    MB,
    RAM,
    HDD,
    SSD,
    CHASSIS,
    PSU;

    companion object {
        fun fromString(type: String): ProductType = when (type) {
            "GPU" -> GPU
            "CPU" -> CPU
            "MB" -> MB
            "RAM" -> RAM
            "HDD" -> HDD
            "SSD" -> SSD
            "CHASSIS" -> CHASSIS
            "PSU" -> PSU
            else -> throw IllegalArgumentException("Unknown product type: $type")
        }
    }
}