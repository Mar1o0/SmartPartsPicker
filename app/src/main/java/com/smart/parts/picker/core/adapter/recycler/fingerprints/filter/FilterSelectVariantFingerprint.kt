package com.smart.parts.picker.core.adapter.recycler.fingerprints.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.Item
import com.smart.parts.picker.core.adapter.recycler.ItemFingerprint
import com.smart.parts.picker.core.adapter.recycler.ItemViewHolder
import com.smart.parts.picker.databinding.ItemFilterSelectVariantBinding
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FilterSelectVariantItem(
    val id: Int,
    val name: String,
    var isSelected: Boolean,
) : FilterItem

class FilterSelectVariantFingerprint(
) : ItemFingerprint<ItemFilterSelectVariantBinding, FilterSelectVariantItem> {

    override fun isRelativeItem(item: Item) = item is FilterSelectVariantItem

    override fun getLayoutResId() = R.layout.item_filter_select_variant

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemFilterSelectVariantBinding, FilterSelectVariantItem> {
        val binding = ItemFilterSelectVariantBinding.inflate(layoutInflater, parent, false)
        return FilterSelectVariantViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FilterSelectVariantItem>() {
        override fun areItemsTheSame(
            oldItem: FilterSelectVariantItem,
            newItem: FilterSelectVariantItem
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FilterSelectVariantItem,
            newItem: FilterSelectVariantItem
        ) = oldItem == newItem
    }
}

class FilterSelectVariantViewHolder(
    binding: ItemFilterSelectVariantBinding,
) : ItemViewHolder<ItemFilterSelectVariantBinding, FilterSelectVariantItem>(binding) {

    override fun onBind(item: FilterSelectVariantItem) {
        super.onBind(item)
        binding.cbSelect.isChecked = item.isSelected
        binding.cbSelect.text = item.name
        binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
        }
    }

}