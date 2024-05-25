package com.vlad.sharaga.domain.adapter.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ItemViewHolder<out VB: ViewBinding, I: Item>(
    val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }

}