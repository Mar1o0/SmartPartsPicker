package com.smart.parts.picker.core.adapter.recycler.fingerprints

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.Item
import com.smart.parts.picker.core.adapter.recycler.ItemFingerprint
import com.smart.parts.picker.core.adapter.recycler.ItemViewHolder
import com.smart.parts.picker.databinding.ItemSpecificationBinding
import com.smart.parts.picker.models.ProductSpec
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SpecificationItem(
    val id: Int,
    val title: String,
    val value: String
) : Item {
    companion object {
        fun create(
            productSpec: ProductSpec
        ): SpecificationItem? {
            if (!productSpec.isValid) return null
            return SpecificationItem(
                id = productSpec.id!!,
                title = productSpec.filterFriendlyName!!,
                value = productSpec.value!!
            )
        }
    }
}

class SpecificationFingerprint : ItemFingerprint<ItemSpecificationBinding, SpecificationItem> {

    override fun isRelativeItem(item: Item) = item is SpecificationItem

    override fun getLayoutResId() = R.layout.item_specification

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemSpecificationBinding, SpecificationItem> {
        val binding = ItemSpecificationBinding.inflate(layoutInflater, parent, false)
        return SpecificationViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<SpecificationItem>() {
        override fun areItemsTheSame(oldItem: SpecificationItem, newItem: SpecificationItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SpecificationItem, newItem: SpecificationItem) =
            oldItem == newItem
    }

}

class SpecificationViewHolder(
    binding: ItemSpecificationBinding
) : ItemViewHolder<ItemSpecificationBinding, SpecificationItem>(binding) {

    override fun onBind(item: SpecificationItem) {
        super.onBind(item)
        binding.tvTitle.text = item.title
        when (item.value) {
            "true" -> {
                binding.ivValue.isVisible = true
                binding.ivValue.setImageResource(R.drawable.ic_check)
                binding.ivValue.imageTintList = ColorStateList.valueOf(
                    context.getColor(R.color.success)
                )
            }

            "false" -> {
                binding.ivValue.isVisible = true
                binding.ivValue.setImageResource(R.drawable.ic_close)
                binding.ivValue.imageTintList = ColorStateList.valueOf(
                    context.getColor(R.color.error)
                )
            }

            else -> {
                binding.tvValue.isVisible = true
                binding.tvValue.text = item.value
            }
        }
    }

}