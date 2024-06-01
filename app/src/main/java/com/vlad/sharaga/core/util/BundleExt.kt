package com.vlad.sharaga.core.util

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat

inline fun <reified T : Parcelable> Bundle?.parcelable(key: String): T? {
    if (this == null) return null
    return BundleCompat.getParcelable(this, key, T::class.java)
}