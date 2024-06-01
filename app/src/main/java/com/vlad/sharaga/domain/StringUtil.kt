package com.vlad.sharaga.domain

fun Double.format(digits: Int) = "%.${digits}f"
    .format(this)
    .replace(".", "," )

fun Float.format(digits: Int) = "%.${digits}f"
    .format(this)
    .replace(".", "," )