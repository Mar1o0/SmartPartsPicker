package com.smart.parts.picker.core.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.smart.parts.picker.R
import com.smart.parts.picker.databinding.ItemProductPreviewBinding
import com.smart.parts.picker.core.adapter.Item
import com.smart.parts.picker.core.adapter.recycler.ItemFingerprint
import com.smart.parts.picker.core.adapter.recycler.ItemViewHolder
import com.smart.parts.picker.models.types.ProductId
import com.smart.parts.picker.domain.format
import com.smart.parts.picker.models.Product
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProductPreviewItem(
    val productId: ProductId,
    val imageUrl: String,
    val title: String,
    val rating: Float,
    val minPrice: Double?,
    val minPriceCurrency: String?,
    val variants: Int?,
) : Item {
    companion object {
        fun create(
            product: Product,
        ): ProductPreviewItem? {
            if (!product.isValid) return null
            val minPrice = product.price!!.filter { it.isValid }.minByOrNull { it.price!! } ?: return null
            return ProductPreviewItem(
                productId = product.id!!,
                title = product.fullName!!,
                imageUrl = product.image!!,
                minPrice = minPrice.price,
                minPriceCurrency = minPrice.currency,
                rating = product.rating!! * 0.1f,
                variants = product.price.size
            )
        }
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
            oldItem.productId == newItem.productId

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
        if (item.minPrice != null && item.minPriceCurrency != null) {
            binding.tvMinPrice.text =
                context.getString(R.string.min_price, item.minPrice.format(2), item.minPriceCurrency)
        }
        binding.btnVariants.text = context.getString(R.string.variants, item.variants)
        binding.btnVariants.setOnClickListener { onVariantsClick(item.productId) }
        Glide.with(context)
            .load(item.imageUrl)
            .into(binding.ivPreview)
        binding.root.setOnClickListener { onClick(item.productId) }
    }

}