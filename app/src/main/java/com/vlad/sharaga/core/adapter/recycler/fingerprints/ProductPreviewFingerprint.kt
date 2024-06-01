package com.vlad.sharaga.core.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemProductPreviewBinding
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.domain.format
import com.vlad.sharaga.models.Product
import com.vlad.sharaga.models.ProductImage
import com.vlad.sharaga.models.ProductPrice
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProductPreviewItem(
    val productId: ProductId,
    val imageUrl: String,
    val title: String,
    val rating: Float,
    val minPrice: Double,
    val variants: Int,
) : Item {
    companion object {
        fun create(
            product: Product,
            productImage: ProductImage,
            productPrices: List<ProductPrice>,
        ) = ProductPreviewItem(
            productId = product.id,
            title = product.fullName,
            imageUrl = productImage.imageUrl,
            minPrice = productPrices.minOf { it.price },
            rating = product.rating,
            variants = productPrices.size
        )
    }
}

class ProductPreviewFingerprint(
    private val onProductClick: (ProductId) -> Unit,
    private val onVariantsClick: (ProductId) -> Unit,
) : ItemFingerprint<ItemProductPreviewBinding, ProductPreviewItem> {

    override fun isRelativeItem(item: Item) = item is ProductPreviewItem

    override fun getLayoutResId() = R.layout.item_product_preview

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemProductPreviewBinding, ProductPreviewItem> {
        val binding = ItemProductPreviewBinding.inflate(layoutInflater, parent, false)
        return ProductPreviewViewHolder(binding, onProductClick, onVariantsClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ProductPreviewItem>() {
        override fun areItemsTheSame(oldItem: ProductPreviewItem, newItem: ProductPreviewItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ProductPreviewItem, newItem: ProductPreviewItem) =
            oldItem == newItem
    }

}

class ProductPreviewViewHolder(
    binding: ItemProductPreviewBinding,
    private val onClick: (ProductId) -> Unit,
    private val onVariantsClick: (ProductId) -> Unit,
) : ItemViewHolder<ItemProductPreviewBinding, ProductPreviewItem>(binding) {

    override fun onBind(item: ProductPreviewItem) {
        super.onBind(item)
        binding.tvTitle.text = item.title
        binding.rbRating.rating = item.rating
        binding.tvRating.text = context.getString(R.string.rating_format, item.rating.format(1))
        binding.tvMinPrice.text = context.getString(R.string.min_price, item.minPrice.format(2))
        binding.btnVariants.text = context.getString(R.string.variants, item.variants)
        binding.btnVariants.setOnClickListener { onVariantsClick(item.productId) }
        Glide.with(context)
            .load(item.imageUrl)
            .into(binding.ivPreview)
        binding.root.setOnClickListener { onClick(item.productId) }
    }

}