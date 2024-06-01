package com.vlad.sharaga.core.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemGameBinding
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class GameItem(
    val id: Int,
    val name: String,
    val previewUrl: String,
) : Item

class GameFingerprint(
    private val onGameClick: (GameItem) -> Unit
) : ItemFingerprint<ItemGameBinding, GameItem> {

    override fun isRelativeItem(item: Item) = item is GameItem

    override fun getLayoutResId() = R.layout.item_game

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemGameBinding, GameItem> {
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding, onGameClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<GameItem>() {
        override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem) = oldItem == newItem
    }
}

class GameViewHolder(
    binding: ItemGameBinding,
    private val onGameClick: (GameItem) -> Unit
) : ItemViewHolder<ItemGameBinding, GameItem>(binding) {

    override fun onBind(item: GameItem) {
        super.onBind(item)
        binding.root.setOnClickListener { onGameClick(item) }
        binding.ivPreview.contentDescription = item.name
        Glide.with(context)
            .load(item.previewUrl)
            .into(binding.ivPreview)
    }

}