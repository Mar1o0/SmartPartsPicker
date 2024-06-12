package com.smart.parts.picker.domain

fun Double.format(digits: Int) = "%.${digits}f"
    .format(this)
    .replace(".", "," )

fun Float.format(digits: Int) = "%.${digits}f"
    .format(this)
    .replace(".", "," )

fun Int.dotFormat() = this.toDouble().format(1)

fun Iterable<Number>.average(): Double {
    var sum = 0.0
    var count = 0

    for (num in this) {
        sum += num.toDouble()
        count++
    }

    return if (count > 0) sum / count else 0.0
}