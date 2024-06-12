package com.smart.parts.picker.core.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.Item
import com.smart.parts.picker.core.adapter.recycler.ItemFingerprint
import com.smart.parts.picker.core.adapter.recycler.ItemViewHolder
import com.smart.parts.picker.databinding.ItemBudgetAssemblyBinding
import com.smart.parts.picker.domain.format
import com.smart.parts.picker.models.Budget
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class BudgetAssemblyItem(
    val id: Int,
    val price: Double,
    val products: List<ProductPreviewItem>,
) : Item {
    companion object {
        fun create(
            budget: Budget
        ): BudgetAssemblyItem? {
            if (!budget.isValid) return null
            return BudgetAssemblyItem(
                id = budget.products.hashCode(),
                price = budget.totalPrice!!,
                products = budget.products!!.mapNotNull { ProductPreviewItem.create(it) }
            )
        }
    }
}

class BudgetAssemblyFingerprint(
    private val onBudgetAssemblyClick: (BudgetAssemblyItem) -> Unit
) : ItemFingerprint<ItemBudgetAssemblyBinding, BudgetAssemblyItem> {

    override fun isRelativeItem(item: Item) = item is BudgetAssemblyItem

    override fun getLayoutResId() = R.layout.item_budget_assembly

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemBudgetAssemblyBinding, BudgetAssemblyItem> {
        val binding = ItemBudgetAssemblyBinding.inflate(layoutInflater, parent, false)
        return BudgetAssemblyViewHolder(binding, onBudgetAssemblyClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<BudgetAssemblyItem>() {
        override fun areItemsTheSame(oldItem: BudgetAssemblyItem, newItem: BudgetAssemblyItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: BudgetAssemblyItem, newItem: BudgetAssemblyItem) =
            oldItem == newItem
    }
}

class BudgetAssemblyViewHolder(
    binding: ItemBudgetAssemblyBinding,
    private val onBudgetAssemblyClick: (BudgetAssemblyItem) -> Unit
) : ItemViewHolder<ItemBudgetAssemblyBinding, BudgetAssemblyItem>(binding) {

    override fun onBind(item: BudgetAssemblyItem) {
        super.onBind(item)
        binding.root.setOnClickListener { onBudgetAssemblyClick(item) }
        binding.tvPrice.text = context.getString(R.string.price, item.price.format(2))
        binding.tvDescription.text = context.getString(R.string.assembly_description, item.products.size)

        val glide = Glide.with(context)
        val previewImages = listOf(binding.ivPreview1, binding.ivPreview2, binding.ivPreview3)
        item.products.take(previewImages.size).forEachIndexed { index, product ->
            previewImages[index].isVisible = true
            glide.load(product.imageUrl).into(previewImages[index])
        }
    }

}