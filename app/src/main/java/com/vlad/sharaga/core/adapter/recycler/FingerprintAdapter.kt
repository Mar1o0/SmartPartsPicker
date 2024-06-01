package com.vlad.sharaga.core.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.vlad.sharaga.core.adapter.Item

class FingerprintAdapter(
    private val fingerprints: List<ItemFingerprint<*, *>>,
) : ListAdapter<Item, ItemViewHolder<ViewBinding, Item>>(
    FingerprintDiffUtil(fingerprints)
) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder<ViewBinding, Item> {
        val inflater = LayoutInflater.from(parent.context)
        return fingerprints.find { it.getLayoutResId() == viewType }
            ?.getViewHolder(inflater, parent)
            ?.let { it as? ItemViewHolder<ViewBinding, Item> }
            ?: throw IllegalArgumentException("Unknown viewType: $viewType")
    }

    override fun onBindViewHolder(holder: ItemViewHolder<ViewBinding, Item>, position: Int) {
        holder.onBind(currentList[position])
    }

    @Suppress("UselessCallOnNotNull")
    override fun onBindViewHolder(
        holder: ItemViewHolder<ViewBinding, Item>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.onBind(currentList[position], payloads)
        }
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return fingerprints.find { it.isRelativeItem(item) }
            ?.getLayoutResId()
            ?: throw IllegalArgumentException("Unknown item: $item")
    }
}