package com.vlad.sharaga.core.adapter.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.vlad.sharaga.core.adapter.Item

abstract class ItemViewHolder<out VB: ViewBinding, I: Item>(
    val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: I

    protected val context get() = binding.root.context

    open fun onBind(item: I) {
        this.item = item
    }

    open fun onBind(item: I, payloads: List<Any>) {
        this.item = item
    }

}