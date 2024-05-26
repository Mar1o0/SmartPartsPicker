package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemTitleBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TitleItem(
    val title: String
): Item

class TitleFingerprint : ItemFingerprint<ItemTitleBinding, TitleItem> {

    override fun isRelativeItem(item: Item) = item is TitleItem

    override fun getLayoutResId() = R.layout.item_title

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemTitleBinding, TitleItem> {
        val binding = ItemTitleBinding.inflate(layoutInflater, parent, false)
        return TitleViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<TitleItem>() {
        override fun areItemsTheSame(oldItem: TitleItem, newItem: TitleItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: TitleItem, newItem: TitleItem) = oldItem == newItem
    }

}

class TitleViewHolder(
    binding: ItemTitleBinding
) : ItemViewHolder<ItemTitleBinding, TitleItem>(binding) {

    override fun onBind(item: TitleItem) {
        super.onBind(item)
        binding.tvTitle.text = item.title
    }

}