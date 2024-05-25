package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemSearchBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder

object SearchItem : Item

class SearchFingerprint : ItemFingerprint<ItemSearchBinding, SearchItem> {

    override fun isRelativeItem(item: Item) = item is SearchItem

    override fun getLayoutResId() = R.layout.item_search

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemSearchBinding, SearchItem> {
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) = true

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) = true
    }
}

class SearchViewHolder(
    binding: ItemSearchBinding
) : ItemViewHolder<ItemSearchBinding, SearchItem>(binding) {

}