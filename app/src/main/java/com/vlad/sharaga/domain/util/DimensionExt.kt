package com.vlad.sharaga.domain.util

import android.content.Context
import android.util.TypedValue


fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)