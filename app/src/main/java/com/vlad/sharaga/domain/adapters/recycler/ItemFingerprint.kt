package com.vlad.sharaga.domain.adapters.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface ItemFingerprint<VB: ViewBinding, I: Item> {

    fun isRelativeItem(item: Item): Boolean

    @LayoutRes
    fun getLayoutResId(): Int

    fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<VB, I>


}

abstract class ItemViewHolder<out VB: ViewBinding, in I: Item>(
    val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: I)

}