package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemSearchBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
object SearchItem : Item

class SearchFingerprint(
    private val onSearch: (String) -> Unit
) : ItemFingerprint<ItemSearchBinding, SearchItem> {

    override fun isRelativeItem(item: Item) = item is SearchItem

    override fun getLayoutResId() = R.layout.item_search

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemSearchBinding, SearchItem> {
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding, onSearch)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem) = true

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem) = true
    }
}

class SearchViewHolder(
    binding: ItemSearchBinding,
    private val onSearch: (String) -> Unit
) : ItemViewHolder<ItemSearchBinding, SearchItem>(binding) {

    override fun onBind(item: SearchItem) {
        super.onBind(item)
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearch(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

}