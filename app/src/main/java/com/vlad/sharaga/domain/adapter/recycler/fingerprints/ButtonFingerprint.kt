package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemButtonBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder

data class ButtonItem(
    val title: String
): Item

class ButtonFingerprint : ItemFingerprint<ItemButtonBinding, ButtonItem> {

    override fun isRelativeItem(item: Item) = item is ButtonItem

    override fun getLayoutResId() = R.layout.item_button

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemButtonBinding, ButtonItem> {
        val binding = ItemButtonBinding.inflate(layoutInflater, parent, false)
        return ButtonViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ButtonItem>() {
        override fun areItemsTheSame(oldItem: ButtonItem, newItem: ButtonItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ButtonItem, newItem: ButtonItem) = oldItem == newItem
    }

}

class ButtonViewHolder(
    binding: ItemButtonBinding
) : ItemViewHolder<ItemButtonBinding, ButtonItem>(binding) {

    override fun onBind(item: ButtonItem) {
        super.onBind(item)
        binding.btnButton.text = item.title
    }

}