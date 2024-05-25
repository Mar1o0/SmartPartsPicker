package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemFeedBinding
import com.vlad.sharaga.databinding.ItemIconTitleBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder

data class FeedItem(
    val id: Int,
    val preview: Bitmap,
    val title: String,
    val rating: Float,
    val minPrice: String,
    val variants: String,
): Item

class FeedFingerprint : ItemFingerprint<ItemFeedBinding, FeedItem> {

    override fun isRelativeItem(item: Item) = item is FeedItem

    override fun getLayoutResId() = R.layout.item_feed

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemFeedBinding, FeedItem> {
        val binding = ItemFeedBinding.inflate(layoutInflater, parent, false)
        return FeedViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem) = oldItem == newItem
    }

}

class FeedViewHolder(
    binding: ItemFeedBinding
) : ItemViewHolder<ItemFeedBinding, FeedItem>(binding) {

    @SuppressLint("SetTextI18n")
    override fun onBind(item: FeedItem) {
        super.onBind(item)
        binding.ivPreview.setImageBitmap(item.preview)
        binding.tvTitle.text = item.title
        binding.rbRating.rating = item.rating
        binding.tvRating.text = "(${item.rating})"
        binding.tvMinPrice.text = item.minPrice
        binding.btnVariants.text = item.variants
    }

}