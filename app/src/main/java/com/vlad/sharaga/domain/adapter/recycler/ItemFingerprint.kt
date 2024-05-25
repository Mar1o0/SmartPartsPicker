package com.vlad.sharaga.domain.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

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

