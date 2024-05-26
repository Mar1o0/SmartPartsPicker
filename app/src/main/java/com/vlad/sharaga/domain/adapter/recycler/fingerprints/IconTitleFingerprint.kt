package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemIconTitleBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class IconTitleItem(
    val iconResId: Int,
    val title: String
): Item

class IconTitleFingerprint : ItemFingerprint<ItemIconTitleBinding, IconTitleItem> {

    override fun isRelativeItem(item: Item) = item is IconTitleItem

    override fun getLayoutResId() = R.layout.item_icon_title

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemIconTitleBinding, IconTitleItem> {
        val binding = ItemIconTitleBinding.inflate(layoutInflater, parent, false)
        return IconTitleViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<IconTitleItem>() {
        override fun areItemsTheSame(oldItem: IconTitleItem, newItem: IconTitleItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: IconTitleItem, newItem: IconTitleItem) = oldItem == newItem
    }

}

class IconTitleViewHolder(
    binding: ItemIconTitleBinding
) : ItemViewHolder<ItemIconTitleBinding, IconTitleItem>(binding) {

    override fun onBind(item: IconTitleItem) {
        super.onBind(item)
        binding.ivIcon.setImageResource(item.iconResId)
        binding.tvTitle.text = item.title
    }

}