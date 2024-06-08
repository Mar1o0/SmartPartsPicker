package com.vlad.sharaga.core.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemCategoryBinding
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.models.ProductType
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CategoryItem(
    val productType: ProductType,
    val title: String,
    val previewUrl: String,
) : Item

class CategoryFingerprint(
    private val onClick: (CategoryItem) -> Unit
) : ItemFingerprint<ItemCategoryBinding, CategoryItem> {

    override fun isRelativeItem(item: Item) = item is CategoryItem

    override fun getLayoutResId() = R.layout.item_category

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemCategoryBinding, CategoryItem> {
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding, onClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem) =
            oldItem.productType == newItem.productType

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem) =
            oldItem == newItem
    }

}

class CategoryViewHolder(
    binding: ItemCategoryBinding,
    private val onClick: (CategoryItem) -> Unit
) : ItemViewHolder<ItemCategoryBinding, CategoryItem>(binding) {

    override fun onBind(item: CategoryItem) {
        super.onBind(item)
        binding.tvTitle.text = item.title
        binding.root.setOnClickListener { onClick(item) }
        Glide.with(context)
            .load(item.previewUrl)
            .into(binding.ivPreview)
    }

}