package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemArticleBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ArticleItem(
    val previewUrl: String,
    val title: String,
    val rating: Float,
    val minPrice: String,
    val variants: String,
): Item

class ArticleFingerprint : ItemFingerprint<ItemArticleBinding, ArticleItem> {

    override fun isRelativeItem(item: Item) = item is ArticleItem

    override fun getLayoutResId() = R.layout.item_article

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemArticleBinding, ArticleItem> {
        val binding = ItemArticleBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ArticleItem>() {
        override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem) = oldItem == newItem
    }

}

class ArticleViewHolder(
    binding: ItemArticleBinding
) : ItemViewHolder<ItemArticleBinding, ArticleItem>(binding) {

    override fun onBind(item: ArticleItem) {
        super.onBind(item)
        binding.tvTitle.text = item.title
        binding.rbRating.rating = item.rating
        binding.tvMinPrice.text = item.minPrice
        binding.btnVariants.text = item.variants
        Glide.with(binding.root.context)
            .load(item.previewUrl)
            .into(binding.ivPreview)
    }

}