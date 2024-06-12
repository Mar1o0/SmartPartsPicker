package com.smart.parts.picker.core.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.smart.parts.picker.core.adapter.Item

interface ItemFingerprint<VB: ViewBinding, I: Item> {

    fun isRelativeItem(item: Item): Boolean

    @LayoutRes
    fun getLayoutResId(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<VB, I>

    fun getDiffUtil(): DiffUtil.ItemCallback<I>

}

