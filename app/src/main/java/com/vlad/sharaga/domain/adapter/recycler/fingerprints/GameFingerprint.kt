package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemGameBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder

data class GameItem(
    val id: Int,
    val preview: Bitmap
) : Item

class GameFingerprint : ItemFingerprint<ItemGameBinding, GameItem> {

    override fun isRelativeItem(item: Item) = item is GameItem

    override fun getLayoutResId() = R.layout.item_game

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemGameBinding, GameItem> {
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<GameItem>() {
        override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem) = oldItem == newItem
    }
}

class GameViewHolder(
    binding: ItemGameBinding
) : ItemViewHolder<ItemGameBinding, GameItem>(binding) {

    override fun onBind(item: GameItem) {
        super.onBind(item)
        binding.ivPreview.setImageBitmap(item.preview)
    }

}